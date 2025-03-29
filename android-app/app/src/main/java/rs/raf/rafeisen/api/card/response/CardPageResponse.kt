package rs.raf.rafeisen.api.card.response

import kotlinx.serialization.Serializable

@Serializable
data class CardPageResponse(
    val content: List<CardResponse>


)



