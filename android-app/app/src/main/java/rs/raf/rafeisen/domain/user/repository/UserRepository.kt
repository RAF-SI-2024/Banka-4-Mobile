package rs.raf.rafeisen.domain.user.repository

import javax.inject.Inject
import rs.raf.rafeisen.domain.client.api.ClientApi
import rs.raf.rafeisen.domain.client.api.response.ClientResponse

class UserRepository @Inject constructor(private val clientApi: ClientApi) {
    suspend fun getCurrentUser(): ClientResponse = clientApi.getMe()
}
