package rs.raf.rafeisen.auth

import kotlinx.coroutines.withContext
import rs.raf.rafeisen.api.auth.AuthApi
import rs.raf.rafeisen.api.auth.request.LoginRequest
import rs.raf.rafeisen.api.client.ClientApi
import rs.raf.rafeisen.coroutines.CoroutineDispatcherProvider
import rs.raf.rafeisen.store.CredentialsStore
import javax.inject.Inject

class AuthService
    @Inject
    constructor(
        private val dispatchers: CoroutineDispatcherProvider,
        private val authApi: AuthApi,
        private val clientApi: ClientApi,
        private val credentialsStore: CredentialsStore,
    ) {
        suspend fun login(
            email: String,
            password: String,
        ) = withContext(dispatchers.io()) {
            val response = authApi.login(body = LoginRequest(email = email, password = password))
            val clientMeResponse = clientApi.getMe()
            // TODO: save these responses in data stores
        }
    }
