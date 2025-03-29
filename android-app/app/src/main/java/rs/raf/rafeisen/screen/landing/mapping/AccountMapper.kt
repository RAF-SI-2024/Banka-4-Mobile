package rs.raf.rafeisen.screen.landing.mapping

import rs.raf.rafeisen.api.account.response.AccountResponse
import rs.raf.rafeisen.screen.landing.db.AccountEntity
import rs.raf.rafeisen.screen.landing.model.AccountUIModel

fun AccountResponse.toEntity(): AccountEntity {
    return AccountEntity(
        id = id,
        accountNumber = accountNumber,
        availableBalance = availableBalance,
        createdDate = createdDate,
        expirationDate = expirationDate,
        accountType = accountType,
        currencyCode = currency.code,
        clientFirstName = client.firstName,
        clientLastName = client.lastName
    )
}

fun AccountEntity.toUiModel(): AccountUIModel {
    return AccountUIModel(
        id = id,
        accountNumber = accountNumber,
        availableBalance = availableBalance,
        createdDate = createdDate,
        expirationDate = expirationDate,
        accountType = accountType,
        currencyCode = currencyCode,
        clientFullName = "$clientFirstName $clientLastName"
    )
}
