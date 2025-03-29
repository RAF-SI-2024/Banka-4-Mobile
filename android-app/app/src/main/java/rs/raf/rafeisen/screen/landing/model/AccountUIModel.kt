package rs.raf.rafeisen.screen.landing.model

data class AccountUIModel(
    val id: String,
    val accountNumber: String,
    val availableBalance: Double,
    val createdDate: String,
    val expirationDate: String,
    val accountType: String,
    val currencyCode: String,
    val clientFullName: String
)
