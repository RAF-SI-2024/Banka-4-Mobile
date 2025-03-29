package rs.raf.rafeisen.screen.landing.model

import rs.raf.rafeisen.model.CardName
import rs.raf.rafeisen.model.CardType

data class CardUIModel(
    val cardNumber: String,
    val cardName: CardName,
    val cardType: CardType,
    val expirationDate: String,
    val clientFullName: String,
)
