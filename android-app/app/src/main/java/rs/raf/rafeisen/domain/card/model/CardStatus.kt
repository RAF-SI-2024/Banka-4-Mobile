package rs.raf.rafeisen.domain.card.model

import kotlinx.serialization.Serializable

@Serializable
enum class CardStatus {
    ACTIVATED,
    DEACTIVATED,
    BLOCKED,
}
