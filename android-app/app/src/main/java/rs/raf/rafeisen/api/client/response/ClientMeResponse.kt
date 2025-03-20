package rs.raf.rafeisen.api.client.response

import kotlinx.serialization.Serializable
import rs.raf.rafeisen.model.Gender

@Serializable
data class ClientMeResponse(
    val id: String,
    val firstName: String,
    val lastName: String,
    val gender: Gender,
    val email: String,
    val phone: String,
    val address: String,
    // TODO: add privilege and dateOfBirth
)
