package rs.raf.rafeisen.domain.card.model

import kotlinx.serialization.Serializable

@Serializable
enum class CardType {
    Debit,
    Credit,
}
