package rs.raf.rafeisen.screen.profile.model

import java.time.LocalDate
import rs.raf.rafeisen.domain.client.model.Gender

data class ProfileUIModel(
    val fullName: String,
    val email: String,
    val phone: String,
    val address: String,
    val dateOfBirth: LocalDate,
    val gender: Gender,
    val has2FA: Boolean,
)
