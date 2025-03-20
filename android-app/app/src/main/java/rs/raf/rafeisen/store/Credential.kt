package rs.raf.rafeisen.store

import kotlinx.serialization.Serializable

@Serializable
data class Credential(
    val id: String,
    val accessToken: String,
    val refreshToken: String
)
