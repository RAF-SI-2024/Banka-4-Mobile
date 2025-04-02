package rs.raf.rafeisen.auth

import retrofit2.http.Body
import retrofit2.http.POST
import rs.raf.rafeisen.auth.request.LoginRequest
import rs.raf.rafeisen.auth.response.LoginResponse

interface AuthApi {
    @POST("/auth/client/login")
    suspend fun login(@Body body: LoginRequest): LoginResponse
}
