package rs.raf.rafeisen.domain.client.api.response

import java.time.LocalDate
import kotlinx.serialization.Serializable
import rs.raf.rafeisen.domain.client.model.Gender
import rs.raf.rafeisen.serialization.custom.LocalDateSerializer

@Serializable
data class ClientResponse(
    val id: String,
    val firstName: String,
    val lastName: String,
    val gender: Gender,
    val email: String,
    val phone: String,
    @Serializable(with = LocalDateSerializer::class)
    val dateOfBirth: LocalDate,
    val address: String,
    val has2FA: Boolean? = null,
)
