package rs.raf.rafeisen.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class CardType {
    @SerialName("DEBIT")
    Debit,

    @SerialName("CREDIT")
    Credit
}
