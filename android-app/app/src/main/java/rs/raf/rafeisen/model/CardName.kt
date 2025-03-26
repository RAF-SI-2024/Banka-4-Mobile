package rs.raf.rafeisen.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class CardName {
    @SerialName("VISA")
    Visa,

    @SerialName("MASTERCARD")
    MasterCard,

    @SerialName("DINACARD")
    DinaCard,

    @SerialName("AMERICAN_EXPRESS")
    AmericanExpress
}
