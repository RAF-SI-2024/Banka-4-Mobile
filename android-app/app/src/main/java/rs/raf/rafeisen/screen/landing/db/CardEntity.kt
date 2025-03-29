package rs.raf.rafeisen.screen.landing.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import rs.raf.rafeisen.model.CardName
import rs.raf.rafeisen.model.CardType

@Entity
data class CardEntity(
    @PrimaryKey val cardNumber: String,
    val cvv: String,
    val cardName: CardName,
    val creationDate: String,
    val expirationDate: String,
    val cardType: CardType,
    val limit: Double,
    val cardStatus: String,
    val accountNumber: String,
    val clientFirstName: String,
    val clientLastName: String,
)
