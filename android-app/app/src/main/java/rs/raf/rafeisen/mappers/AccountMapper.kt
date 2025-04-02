package rs.raf.rafeisen.mappers

import rs.raf.rafeisen.domain.account.api.response.AccountResponse
import rs.raf.rafeisen.domain.account.model.Account
import rs.raf.rafeisen.screen.home.model.AccountUIModel

fun AccountResponse.toEntity(): Account =
    Account(
        id = id,
        accountNumber = accountNumber,
        availableBalance = availableBalance,
        createdDate = createdDate,
        expirationDate = expirationDate,
        accountType = accountType,
        currencyCode = currency.code,
        clientFirstName = client.firstName,
        clientLastName = client.lastName,
    )

fun Account.toUiModel(): AccountUIModel =
    AccountUIModel(
        id = id,
        accountNumber = accountNumber,
        availableBalance = availableBalance,
        createdDate = createdDate,
        expirationDate = expirationDate,
        accountType = accountType,
        currencyCode = currencyCode,
        clientFullName = "$clientFirstName $clientLastName",
    )
