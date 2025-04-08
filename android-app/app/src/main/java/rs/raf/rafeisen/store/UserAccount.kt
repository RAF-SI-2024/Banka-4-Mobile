package rs.raf.rafeisen.store

import kotlinx.serialization.Serializable
import rs.raf.rafeisen.domain.client.model.Gender

@Serializable
data class UserAccount(
    val id: String,
    val firstName: String,
    val lastName: String,
    val gender: Gender,
    val email: String,
    val phone: String,
    val address: String,
    val dateOfBirth: String,
    val has2FA: Boolean? = false,
) {
    fun fullName() = "$firstName $lastName"
    companion object {
        val EMPTY = UserAccount(
            id = "",
            firstName = "",
            lastName = "",
            gender = Gender.Male,
            email = "",
            phone = "",
            address = "",
            dateOfBirth = "",
            has2FA = false,
        )
    }
}
