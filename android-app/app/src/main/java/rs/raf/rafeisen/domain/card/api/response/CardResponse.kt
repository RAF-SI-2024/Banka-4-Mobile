package rs.raf.rafeisen.domain.card.api.response

import java.math.BigDecimal
import java.time.LocalDate
import kotlinx.serialization.Serializable
import rs.raf.rafeisen.domain.authorized_user.api.response.AuthorizedUserResponse
import rs.raf.rafeisen.domain.card.model.CardStatus
import rs.raf.rafeisen.domain.card.model.CardName
import rs.raf.rafeisen.domain.card.model.CardType
import rs.raf.rafeisen.domain.client.api.response.ClientResponse
import rs.raf.rafeisen.serialization.custom.BigDecimalSerializer
import rs.raf.rafeisen.serialization.custom.LocalDateSerializer

@Serializable
data class CardResponse(
    val cardNumber: String,
    val cvv: String,
    val cardName: CardName,
    @Serializable(with = LocalDateSerializer::class)
    val creationDate: LocalDate,
    @Serializable(with = LocalDateSerializer::class)
    val expirationDate: LocalDate,
    val cardType: CardType,
    @Serializable(with = BigDecimalSerializer::class)
    val limit: BigDecimal,
    val cardStatus: CardStatus,
    val accountNumber: String,
    val client: ClientResponse?,
    val authorizedUser: AuthorizedUserResponse?,
)
