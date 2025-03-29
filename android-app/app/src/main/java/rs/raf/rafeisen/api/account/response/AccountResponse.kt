package rs.raf.rafeisen.api.account.response

import kotlinx.serialization.Serializable
import rs.raf.rafeisen.model.Gender


@Serializable
data class AccountResponse(
    val id: String,
    val accountNumber: String,
    val balance: Double,
    val availableBalance: Double,
    val accountMaintenance: Double,
    val createdDate: String,
    val expirationDate: String,
    val active: Boolean,
    val accountType: String,
    val dailyLimit: Double,
    val monthlyLimit: Double,
    val currency: CurrencyResponse,
    val employee: EmployeeResponse,
    val client: ClientResponse,
    val company: CompanyResponse? = null
)

@Serializable
data class CurrencyResponse(
    val id: String,
    val name: String,
    val symbol: String,
    val description: String,
    val active: Boolean,
    val code: String
)

@Serializable
data class EmployeeResponse(
    val id: String,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: String,
    val gender: Gender,
    val email: String,
    val phone: String,
    val address: String,
    val username: String,
    val position: String,
    val department: String,
    val active: Boolean
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
    val privileges: List<String>,
    val has2FA: Boolean? = null
)

@Serializable
data class CompanyResponse(
    val id: String,
    val name: String,
    val tin: String,
    val crn: String,
    val address: String,
    val activityCode: String
)
