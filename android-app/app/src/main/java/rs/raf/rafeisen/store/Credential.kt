package rs.raf.rafeisen.store

import java.time.Instant

data class Credential(
    val id: String,
    val accessToken: String,
    val refreshToken: String,
    val expiresAt: Long = Instant.now().plusSeconds(150).epochSecond,
) {
    fun isExpired() = Instant.ofEpochSecond(expiresAt).isBefore(Instant.now())
}
