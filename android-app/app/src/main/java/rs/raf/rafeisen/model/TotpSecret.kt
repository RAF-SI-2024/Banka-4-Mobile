package rs.raf.rafeisen.model
import kotlinx.serialization.Serializable


@Serializable
data class TotpSecret(
    val userId: String,
    val issuer: String,
    val secret: String,
    val createdAt: Long = System.currentTimeMillis()
)
