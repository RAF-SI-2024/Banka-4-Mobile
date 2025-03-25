package rs.raf.rafeisen.screen.landing.model

import java.time.LocalDate

data class CardDto(
    val cardNumber: String,
    val cvv: String,
    val cardName: String,         //  "Visa", "MasterCard", "DinaCard", "American Express"
    val creationDate: LocalDate,
    val expirationDate: LocalDate,
    val cardType: String,         //  "Debit" ili "Credit"
    val limit: Double,
    val cardStatus: String,       //  "ACTIVE"
    val accountNumber: String,
    val client: ClientDto,
    val authorizedUser: AuthorizedUserDto?
)

data class ClientDto(
    val id: String,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: LocalDate,
    val gender: String,
    val email: String,
    val phone: String,
    val address: String,
    val privileges: List<String>,
    val has2FA: Boolean
)

data class AuthorizedUserDto(
    val id: String,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: LocalDate,
    val gender: String,
    val email: String,
    val phoneNumber: String,
    val address: String
)
