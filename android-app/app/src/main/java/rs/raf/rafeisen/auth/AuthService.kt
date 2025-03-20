package rs.raf.rafeisen.auth

import kotlinx.coroutines.withContext
import rs.raf.rafeisen.api.auth.AuthApi
import rs.raf.rafeisen.api.auth.request.LoginRequest
import rs.raf.rafeisen.coroutines.CoroutineDispatcherProvider
import rs.raf.rafeisen.networking.api.AuthClientServiceInternalRequests
import rs.raf.rafeisen.store.ActiveAccountStore
import rs.raf.rafeisen.store.Credential
import rs.raf.rafeisen.store.CredentialsStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthService
    @Inject
    constructor(
        private val dispatchers: CoroutineDispatcherProvider,
        private val authApi: AuthApi,
        private val authClientServiceInternalRequests: AuthClientServiceInternalRequests,
        private val credentialsStore: CredentialsStore,
        private val activeAccountStore: ActiveAccountStore,
    ) {
        suspend fun login(
            email: String,
            password: String,
        ) = withContext(dispatchers.io()) {
            val response = authApi.login(body = LoginRequest(email = email, password = password))
            // TODO(arsen): handle employees
            val clientMeResponse = authClientServiceInternalRequests.getClientMe("Bearer ${response.accessToken}")
            credentialsStore.addCredential(Credential(
                id = clientMeResponse.id,
                accessToken = response.accessToken,
                refreshToken = response.refreshToken
            ))
            activeAccountStore.setActiveUserId(clientMeResponse.id)
        }
    }
