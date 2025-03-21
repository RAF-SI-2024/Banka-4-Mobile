package rs.raf.rafeisen.auth

import kotlinx.coroutines.withContext
import rs.raf.rafeisen.api.auth.AuthApi
import rs.raf.rafeisen.api.auth.request.LoginRequest
import rs.raf.rafeisen.coroutines.CoroutineDispatcherProvider
import rs.raf.rafeisen.networking.api.AuthClientServiceInternalRequests
import rs.raf.rafeisen.store.AccountsStore
import rs.raf.rafeisen.store.ActiveAccountStore
import rs.raf.rafeisen.store.Credential
import rs.raf.rafeisen.store.CredentialsStore
import rs.raf.rafeisen.store.UserAccount
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthService @Inject constructor(
    private val dispatchers: CoroutineDispatcherProvider,
    private val authApi: AuthApi,
    private val authClientServiceInternalRequests: AuthClientServiceInternalRequests,
    private val credentialsStore: CredentialsStore,
    private val accountsStore: AccountsStore,
    private val activeAccountStore: ActiveAccountStore,
) {
    suspend fun login(
        email: String,
        password: String,
    ) = withContext(dispatchers.io()) {
        val response = authApi.login(body = LoginRequest(email = email, password = password))
        val clientMeResponse = authClientServiceInternalRequests
            .getClientMe("Bearer ${response.accessToken}")
        credentialsStore.addCredential(
            Credential(
                id = clientMeResponse.id,
                accessToken = response.accessToken,
                refreshToken = response.refreshToken,
            )
        )
        accountsStore.upsertAccount(
            UserAccount(
                id = clientMeResponse.id,
                firstName = clientMeResponse.firstName,
                lastName = clientMeResponse.lastName,
                email = clientMeResponse.email,
                phone = clientMeResponse.phone,
                gender = clientMeResponse.gender,
                address = clientMeResponse.address,
            )
        )
        activeAccountStore.setActiveUserId(clientMeResponse.id)
    }

    suspend fun logout() =
        withContext(dispatchers.io()) {
            /* TODO: call backend to invalidate jwt */
            accountsStore.deleteAccount(userId = activeAccountStore.activeUserId())
            activeAccountStore.clearActiveUserAccount()
        }

}
