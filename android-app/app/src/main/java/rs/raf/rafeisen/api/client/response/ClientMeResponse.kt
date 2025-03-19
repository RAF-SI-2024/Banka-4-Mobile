package rs.raf.rafeisen.api.client.response

import rs.raf.rafeisen.model.Gender

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
