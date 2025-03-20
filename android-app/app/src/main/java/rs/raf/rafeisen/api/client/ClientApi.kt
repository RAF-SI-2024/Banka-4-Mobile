package rs.raf.rafeisen.api.client

import retrofit2.http.GET
import rs.raf.rafeisen.api.client.response.ClientMeResponse

interface ClientApi {
    @GET("/client/me")
    suspend fun getMe(): ClientMeResponse
}
