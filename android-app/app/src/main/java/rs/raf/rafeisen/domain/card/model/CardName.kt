package rs.raf.rafeisen.domain.card.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class CardName {
    @SerialName("VISA")
    Visa,

    @SerialName("MASTER_CARD")
    MasterCard,

    @SerialName("DINA_CARD")
    DinaCard,

    @SerialName("AMERICAN_EXPRESS")
    AmericanExpress,
}
