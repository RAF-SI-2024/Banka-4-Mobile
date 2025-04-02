package rs.raf.rafeisen.domain.card.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.time.LocalDate

@Entity
data class Card(
    @PrimaryKey val cardNumber: String,
    val cvv: String,
    val cardName: CardName,
    val creationDate: LocalDate,
    val expirationDate: LocalDate,
    val cardType: CardType,
    val limit: BigDecimal,
    val cardStatus: CardStatus,
    val accountNumber: String,
    val clientFirstName: String?,
    val clientLastName: String?,
)
