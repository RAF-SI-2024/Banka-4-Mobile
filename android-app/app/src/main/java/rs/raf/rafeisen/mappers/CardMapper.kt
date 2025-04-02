package rs.raf.rafeisen.mappers

import rs.raf.rafeisen.domain.card.api.response.CardResponse
import rs.raf.rafeisen.domain.card.model.Card
import rs.raf.rafeisen.screen.home.model.CardUIModel

fun CardResponse.toEntity(): Card {
    return Card(
        cardNumber = cardNumber,
        cvv = cvv,
        cardName = cardName,
        creationDate = creationDate,
        expirationDate = expirationDate,
        cardType = cardType,
        limit = limit,
        cardStatus = cardStatus,
        accountNumber = accountNumber,
        clientFirstName = client?.firstName ?: authorizedUser?.firstName,
        clientLastName = client?.lastName ?: authorizedUser?.lastName,
    )
}

fun Card.toUiModel(): CardUIModel {
    return CardUIModel(
        cardNumber = cardNumber,
        cardName = cardName,
        cardType = cardType,
        expirationDate = expirationDate,
        clientFullName = "$clientFirstName $clientLastName"
    )
}
