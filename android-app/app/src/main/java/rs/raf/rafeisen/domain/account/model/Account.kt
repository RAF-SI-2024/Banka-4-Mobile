package rs.raf.rafeisen.domain.account.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.time.LocalDate
import rs.raf.rafeisen.domain.currency.CurrencyCode

@Entity
data class Account(
    @PrimaryKey val id: String,
    val accountNumber: String,
    val availableBalance: BigDecimal,
    val createdDate: LocalDate,
    val expirationDate: LocalDate,
    val accountType: AccountType,
    val currencyCode: CurrencyCode,
    /* TODO: rethink this. */
    val clientFirstName: String,
    val clientLastName: String
)
