package rs.raf.rafeisen.domain.client.api

import retrofit2.http.GET
import rs.raf.rafeisen.domain.client.api.response.ClientResponse

interface ClientApi {
    @GET("/client/me")
    suspend fun getMe(): ClientResponse
}
