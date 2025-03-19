package rs.raf.rafeisen.store

import rs.raf.rafeisen.model.Gender

data class UserAccount(
    val id: String,
    val firstName: String,
    val lastName: String,
    val gender: Gender,
    val email: String,
    val phone: String,
    val address: String,
    // TODO: add privileges and dateOfBirth
)
