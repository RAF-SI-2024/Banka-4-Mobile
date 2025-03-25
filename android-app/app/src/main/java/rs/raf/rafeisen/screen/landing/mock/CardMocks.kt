package rs.raf.rafeisen.screen.landing.mock

import rs.raf.rafeisen.screen.landing.model.AuthorizedUserDto
import rs.raf.rafeisen.screen.landing.model.CardDto
import rs.raf.rafeisen.screen.landing.model.ClientDto
import java.time.LocalDate

val mockCards = listOf(
    CardDto(
        cardNumber = "1234567890123456",
        cvv = "123",
        cardName = "Visa",
        creationDate = LocalDate.parse("1990-05-15"),
        expirationDate = LocalDate.parse("1994-05-15"),
        cardType = "Debit",
        limit = 5000.0,
        cardStatus = "ACTIVE",
        accountNumber = "215351385938112",
        client = ClientDto(
            id = "1fad2c01-f82f-41a6-822c-8ca1b3232575",
            firstName = "Mehmedalija",
            lastName = "Doe",
            dateOfBirth = LocalDate.parse("1990-01-01"),
            gender = "MALE",
            email = "danny.jo@example.com",
            phone = "+381684523697",
            address = "123",
            privileges = listOf("CAN_TRADE"),
            has2FA = false
        ),
        authorizedUser = AuthorizedUserDto(
            id = "11111111-2222-3333-4444-555555555555",
            firstName = "Petar",
            lastName = "Petrović",
            dateOfBirth = LocalDate.parse("1990-05-15"),
            gender = "M",
            email = "petar@example.com",
            phoneNumber = "+381611234567",
            address = "Njegoševa 25"
        )
    ),
    CardDto(
        cardNumber = "9876543210987654",
        cvv = "456",
        cardName = "MasterCard",
        creationDate = LocalDate.of(1991, 6, 20),
        expirationDate = LocalDate.of(1995, 6, 20),
        cardType = "Debit",
        limit = 6000.0,
        cardStatus = "ACTIVE",
        accountNumber = "215351385938113",
        client = ClientDto(
            id = "2fad2c01-f82f-41a6-822c-8ca1b3232576",
            firstName = "Ana",
            lastName = "Smith",
            dateOfBirth = LocalDate.of(1989, 2, 15),
            gender = "FEMALE",
            email = "ana.smith@example.com",
            phone = "+381684523698",
            address = "456",
            privileges = listOf("CAN_TRADE"),
            has2FA = true
        ),
        authorizedUser = null
    ),
    CardDto(
        cardNumber = "1111222233334444",
        cvv = "789",
        cardName = "DinaCard",
        creationDate = LocalDate.of(1992, 7, 10),
        expirationDate = LocalDate.of(1996, 7, 10),
        cardType = "Debit",
        limit = 7000.0,
        cardStatus = "ACTIVE",
        accountNumber = "215351385938114",
        client = ClientDto(
            id = "3fad2c01-f82f-41a6-822c-8ca1b3232577",
            firstName = "Marko",
            lastName = "Ivić",
            dateOfBirth = LocalDate.of(1990, 3, 25),
            gender = "MALE",
            email = "marko@example.com",
            phone = "+381684523699",
            address = "789",
            privileges = listOf("CAN_TRADE"),
            has2FA = false
        ),
        authorizedUser = AuthorizedUserDto(
            id = "22222222-3333-4444-5555-666666666666",
            firstName = "Jelena",
            lastName = "Janković",
            dateOfBirth = LocalDate.of(1991, 8, 15),
            gender = "FEMALE",
            email = "jelena@example.com",
            phoneNumber = "+381611234568",
            address = "Njegoševa 25"
        )
    ),
    CardDto(
        cardNumber = "5555666677778888",
        cvv = "321",
        cardName = "American Express",
        creationDate = LocalDate.of(1993, 8, 5),
        expirationDate = LocalDate.of(1997, 8, 5),
        cardType = "Credit",
        limit = 10000.0,
        cardStatus = "ACTIVE",
        accountNumber = "215351385938115",
        client = ClientDto(
            id = "4fad2c01-f82f-41a6-822c-8ca1b3232578",
            firstName = "John",
            lastName = "Doe",
            dateOfBirth = LocalDate.of(1985, 12, 12),
            gender = "MALE",
            email = "john.doe@example.com",
            phone = "+381684523700",
            address = "101",
            privileges = listOf("CAN_TRADE"),
            has2FA = true
        ),
        authorizedUser = null
    )
)
