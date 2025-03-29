package rs.raf.rafeisen.screen.landing.mapping

import rs.raf.rafeisen.api.card.response.CardResponse
import rs.raf.rafeisen.screen.landing.db.CardEntity
import rs.raf.rafeisen.screen.landing.model.CardUIModel

fun CardResponse.toEntity(): CardEntity {
    return CardEntity(
        cardNumber = cardNumber,
        cvv = cvv,
        cardName = cardName,
        creationDate = creationDate.toString(),
        expirationDate = expirationDate.toString(),
        cardType = cardType,
        limit = limit,
        cardStatus = cardStatus,
        accountNumber = accountNumber,
        clientFirstName = client.firstName,
        clientLastName = client.lastName
    )
}

fun CardEntity.toUiModel(): CardUIModel {
    return CardUIModel(
        cardNumber = cardNumber,
        cardName = cardName,
        cardType = cardType,
        expirationDate = expirationDate,
        clientFullName = "$clientFirstName $clientLastName"
    )
}
