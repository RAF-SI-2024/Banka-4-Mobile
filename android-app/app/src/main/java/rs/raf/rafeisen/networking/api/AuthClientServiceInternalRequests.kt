package rs.raf.rafeisen.networking.api

import kotlinx.serialization.Serializable
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import rs.raf.rafeisen.api.client.response.ClientMeResponse

/** Retrofit interface for requests used by the authentication service and client.
 *  These are hand-authenticated, because otherwise we'd have a cycle.
 *
 *  <p> DO NOT USE IN THE GENERAL CASE.
 */
interface AuthClientServiceInternalRequests {
    @Serializable
    data class TokenRefreshRequest(val refreshToken: String);
    @Serializable
    data class TokenRefreshResponse(val accessToken: String);

    @POST("/auth/refresh-token")
    fun refreshToken(@Body tokenRefreshRequest: TokenRefreshRequest): Call<TokenRefreshResponse>

    @GET("/client/me")
    suspend fun getClientMe(@Header("Authorization") authorization: String): ClientMeResponse
}
