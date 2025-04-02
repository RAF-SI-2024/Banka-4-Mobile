package rs.raf.rafeisen.domain.card.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class CardType {
    @SerialName("DEBIT")
    Debit,

    @SerialName("CREDIT")
    Credit,
}
