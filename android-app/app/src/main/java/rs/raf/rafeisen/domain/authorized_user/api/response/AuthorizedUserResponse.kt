package rs.raf.rafeisen.domain.authorized_user.api.response

import java.time.LocalDate
import kotlinx.serialization.Serializable
import rs.raf.rafeisen.domain.client.model.Gender
import rs.raf.rafeisen.serialization.custom.LocalDateSerializer

@Serializable
data class AuthorizedUserResponse(
    val id: String,
    val firstName: String,
    val lastName: String,
    @Serializable(with = LocalDateSerializer::class)
    val dateOfBirth: LocalDate,
    val gender: Gender,
    val email: String,
    val phoneNumber: String,
    val address: String
)
