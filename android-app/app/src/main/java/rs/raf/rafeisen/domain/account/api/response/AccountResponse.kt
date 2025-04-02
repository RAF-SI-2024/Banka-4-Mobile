package rs.raf.rafeisen.domain.account.api.response

import java.math.BigDecimal
import java.time.LocalDate
import kotlinx.serialization.Serializable
import rs.raf.rafeisen.domain.account.model.AccountType
import rs.raf.rafeisen.domain.client.api.response.ClientResponse
import rs.raf.rafeisen.domain.company.api.model.CompanyResponse
import rs.raf.rafeisen.domain.currency.api.response.CurrencyResponse
import rs.raf.rafeisen.domain.employee.api.response.EmployeeResponse
import rs.raf.rafeisen.serialization.custom.BigDecimalSerializer
import rs.raf.rafeisen.serialization.custom.LocalDateSerializer


@Serializable
data class AccountResponse(
    val id: String,
    val accountNumber: String,
    @Serializable(with = BigDecimalSerializer::class)
    val balance: BigDecimal,
    @Serializable(with = BigDecimalSerializer::class)
    val availableBalance: BigDecimal,
    @Serializable(with = BigDecimalSerializer::class)
    val accountMaintenance: BigDecimal,
    @Serializable(with = LocalDateSerializer::class)
    val createdDate: LocalDate,
    @Serializable(with = LocalDateSerializer::class)
    val expirationDate: LocalDate,
    val active: Boolean,
    val accountType: AccountType,
    @Serializable(with = BigDecimalSerializer::class)
    val dailyLimit: BigDecimal,
    @Serializable(with = BigDecimalSerializer::class)
    val monthlyLimit: BigDecimal,
    val currency: CurrencyResponse,
    val employee: EmployeeResponse,
    val client: ClientResponse,
    val company: CompanyResponse? = null
)

