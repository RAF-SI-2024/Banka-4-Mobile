package rs.raf.rafeisen.networking.utils

import android.util.Pair
import androidx.core.util.component1
import androidx.core.util.component2
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import okhttp3.OkHttpClient
import rs.raf.rafeisen.networking.api.AuthClientServiceInternalRequests
import rs.raf.rafeisen.store.ActiveAccountStore
import rs.raf.rafeisen.store.CredentialsStore
import rs.raf.rafeisen.utils.removePrefixIgnoreCase

/** Error response body, only suitable for local use.  Should be removed when error handling code is
 *  written.
 */
@Serializable
private data class ErrorResponse(val code: String)

private val errorDecoder = Json { ignoreUnknownKeys = true }

@OptIn(ExperimentalSerializationApi::class)
fun buildAuthenticatedOkHttpClient(
    credentialsStore: CredentialsStore,
    activeAccountStore: ActiveAccountStore,
    authClientServiceInternalRequests: AuthClientServiceInternalRequests,
    furtherConfig: OkHttpClient.Builder.() -> OkHttpClient.Builder,
): OkHttpClient {
    val syncKey = object {}
    return OkHttpClient.Builder()
        .authenticator { _, response ->
            /* Try to figure out if this is something we can retry.  */
            if (response.code != 401) return@authenticator null
            try {
                val code = response.body?.let {
                    errorDecoder.decodeFromStream<ErrorResponse>(it.byteStream()).code
                }
                if (code != "ExpiredJwt") {
                    /* Some unrelated error.  */
                    return@authenticator null
                }
            } catch (error: Throwable) {
                return@authenticator null
            }

            val authKey = synchronized(syncKey) {
                val (currentUserCreds, myUserId) =
                    try {
                        runBlocking {
                            val myUserId = activeAccountStore.activeUserId()
                            Pair(credentialsStore.findOrThrow(myUserId), myUserId)
                        }
                    } catch (_: Throwable) {
                        /* Got logged out?  */
                        return@authenticator null
                    }

                val currentAuthKey = currentUserCreds.accessToken
                if (response.request.headers["Authorization"]
                        ?.removePrefixIgnoreCase("bearer ") != currentAuthKey
                ) {
                    /* Someone else did the refresh.  */
                    currentAuthKey
                } else {
                    val newToken = authClientServiceInternalRequests
                        .refreshToken(
                            AuthClientServiceInternalRequests.TokenRefreshRequest(
                                refreshToken = currentUserCreds.refreshToken,
                            ),
                        )
                        .execute()
                        .body()
                        ?.accessToken
                        /* I hate Java.  This happens because there's no way to propagate
                           non-nullability through Retrofit Call<>. */
                        ?: throw RuntimeException("Access token refresh failed in an impossible matter?")
                    runBlocking {
                        credentialsStore.updateAccessToken(myUserId, newToken)
                    }
                    newToken
                }
            }

            response.request
                .newBuilder()
                .header("Authorization", "Bearer $authKey")
                .build()
        }
        .addInterceptor { chain ->
            /* Add an Authorization header if one does not exist.  */
            val req = chain.request()
            chain.proceed(
                if (req.headers["Authorization"] != null) {
                    req
                } else {
                    synchronized(syncKey) {
                        runBlocking {
                            val userId = activeAccountStore.activeUserId()
                            try {
                                req.newBuilder()
                                    .header(
                                        "Authorization",
                                        "Bearer ${credentialsStore.findOrThrow(userId).accessToken}",
                                    )
                                    .build()
                            } catch (_: Throwable) {
                                req
                            }
                        }
                    }
                },
            )
        }
        .run(furtherConfig)
        .build()
}
