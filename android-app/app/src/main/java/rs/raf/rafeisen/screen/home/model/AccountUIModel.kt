package rs.raf.rafeisen.screen.home.model

import java.math.BigDecimal
import java.time.LocalDate
import rs.raf.rafeisen.domain.account.model.AccountType
import rs.raf.rafeisen.domain.currency.CurrencyCode

data class AccountUIModel(
    val id: String,
    val accountNumber: String,
    val availableBalance: BigDecimal,
    val createdDate: LocalDate,
    val expirationDate: LocalDate,
    val accountType: AccountType,
    val currencyCode: CurrencyCode,
    val clientFullName: String,
)
