package rs.raf.rafeisen.domain.card.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class CardName {
    @SerialName("Visa")
    Visa,

    @SerialName("MasterCard")
    MasterCard,

    @SerialName("DinaCard")
    DinaCard,

    @SerialName("American Express")
    AmericanExpress
}
