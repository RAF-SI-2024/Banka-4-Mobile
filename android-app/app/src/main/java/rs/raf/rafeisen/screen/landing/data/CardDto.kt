package rs.raf.rafeisen.screen.landing.data

import rs.raf.rafeisen.model.CardName
import rs.raf.rafeisen.model.CardType
import rs.raf.rafeisen.model.Gender
import java.time.LocalDate

data class CardDto(
    val cardNumber: String,
    val cvv: String,
    val cardName: CardName,
    val creationDate: LocalDate,
    val expirationDate: LocalDate,
    val cardType: CardType,
    val limit: Double,
    val cardStatus: String,
    val accountNumber: String,
    val client: ClientDto,
    val authorizedUser: AuthorizedUserDto?
)

data class ClientDto(
    val id: String,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: LocalDate,
    val gender: Gender,
    val email: String,
    val phone: String,
    val address: String,
    val has2FA: Boolean
)

data class AuthorizedUserDto(
    val id: String,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: LocalDate,
    val gender: Gender,
    val email: String,
    val phoneNumber: String,
    val address: String
)
