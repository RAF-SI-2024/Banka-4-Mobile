package rs.raf.rafeisen.auth.response

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(val accessToken: String, val refreshToken: String)
