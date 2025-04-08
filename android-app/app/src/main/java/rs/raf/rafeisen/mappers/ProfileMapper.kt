package rs.raf.rafeisen.mappers

import java.time.LocalDate
import rs.raf.rafeisen.screen.profile.model.ProfileUIModel
import rs.raf.rafeisen.store.UserAccount

fun UserAccount.toProfileUIModel(): ProfileUIModel =
    ProfileUIModel(
        fullName = "$firstName $lastName",
        email = email,
        phone = phone,
        address = address,
        dateOfBirth = LocalDate.now(),
        gender = gender,
        has2FA = true,
    )
