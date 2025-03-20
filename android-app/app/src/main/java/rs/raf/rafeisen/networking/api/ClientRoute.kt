package rs.raf.rafeisen.networking.api

import kotlinx.serialization.Serializable
import retrofit2.http.GET

interface ClientRoute {
    @GET("/client/me")
    suspend fun getClientMe(): ThingTest
}

@Serializable
data class ThingTest(val id: String)
