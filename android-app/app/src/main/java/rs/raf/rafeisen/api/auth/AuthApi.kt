package rs.raf.rafeisen.api.auth

import retrofit2.http.Body
import retrofit2.http.POST
import rs.raf.rafeisen.api.auth.request.LoginRequest
import rs.raf.rafeisen.api.auth.response.LoginResponse

interface AuthApi {
    @POST("/auth/client/login")
    suspend fun login(
        @Body body: LoginRequest,
    ): LoginResponse
}
