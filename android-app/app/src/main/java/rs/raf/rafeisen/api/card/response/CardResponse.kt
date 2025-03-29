package rs.raf.rafeisen.api.card.response

import kotlinx.serialization.Serializable
import rs.raf.rafeisen.model.CardName
import rs.raf.rafeisen.model.CardType
import rs.raf.rafeisen.model.Gender

@Serializable
data class CardResponse(
    val cardNumber: String,
    val cvv: String,
    val cardName: CardName,
    val creationDate: String,
    val expirationDate: String,
    val cardType: CardType,
    val limit: Double,
    val cardStatus: String,
    val accountNumber: String,
    val client: ClientResponse,
    val authorizedUser: AuthorizedUserResponse? = null
)

@Serializable
data class ClientResponse(
    val id: String,
    val firstName: String,
    val lastName: String,
   val dateOfBirth: String,
    val gender: Gender,
    val email: String,
    val phone: String,
    val address: String,
    val has2FA: Boolean? = null
)

@Serializable
data class AuthorizedUserResponse(
    val id: String,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: String,
    val gender: Gender,
    val email: String,
    val phoneNumber: String,
    val address: String
)
