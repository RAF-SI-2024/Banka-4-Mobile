package rs.raf.rafeisen.domain.currency.api.response

import kotlinx.serialization.Serializable
import rs.raf.rafeisen.domain.currency.CurrencyCode

@Serializable
data class CurrencyResponse(
    val name: String,
    val symbol: String,
    val description: String,
    val active: Boolean,
    val code: CurrencyCode,
)
