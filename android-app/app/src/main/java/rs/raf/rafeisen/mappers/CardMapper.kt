package rs.raf.rafeisen.mappers

import rs.raf.rafeisen.domain.card.api.response.CardResponse
import rs.raf.rafeisen.domain.card.model.Card
import rs.raf.rafeisen.screen.home.model.CardUIModel

fun CardResponse.toEntity(): Card =
    Card(
        cardNumber = cardNumber,
        cvv = cvv,
        cardName = cardName,
        creationDate = creationDate,
        expirationDate = expirationDate,
        cardType = cardType,
        limit = limit,
        cardStatus = cardStatus,
        accountNumber = accountNumber,
        clientFirstName = client?.firstName ?: authorizedUserDto?.firstName,
        clientLastName = client?.lastName ?: authorizedUserDto?.lastName,
    )

fun Card.toUiModel(): CardUIModel =
    CardUIModel(
        cardNumber = cardNumber,
        cardName = cardName,
        cardType = cardType,
        expirationDate = expirationDate,
        clientFullName = "$clientFirstName $clientLastName",
    )
