package rs.raf.rafeisen.mappers

import rs.raf.rafeisen.domain.client.api.response.ClientResponse
import rs.raf.rafeisen.screen.profile.model.ProfileUIModel

fun ClientResponse.toUiModel() =
    ProfileUIModel(
        fullName = "$firstName $lastName",
        email = email,
        phone = phone,
        address = address,
        dateOfBirth = dateOfBirth,
        gender = gender,
        has2FA = has2FA == true,
    )
