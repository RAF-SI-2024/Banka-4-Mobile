package rs.raf.rafeisen.screen.home.model

import java.time.LocalDate
import rs.raf.rafeisen.domain.card.model.CardName
import rs.raf.rafeisen.domain.card.model.CardType

data class CardUIModel(
    val cardNumber: String,
    val cardName: CardName,
    val cardType: CardType,
    val expirationDate: LocalDate,
    val clientFullName: String,
)
