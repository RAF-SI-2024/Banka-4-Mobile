package rs.raf.rafeisen.screen.home.mock

import rs.raf.rafeisen.api.client.response.ClientMeResponse
import rs.raf.rafeisen.model.Gender

data class AccountMock(
    val id: String,
    val accountNumber: String,
    val availableBalance: Double,
    val currency: String,
    val accountName: String,
    val incomingPayments: Double,
    val outgoingPayments: Double,
    val allowedOverdraft: Double
)

data class TransactionMock(
    val id: String,
    val date: String,        // For mock purposes
    val description: String,
    val amount: Double,
    val currency: String
)

object HomeMockData {

    fun getMockClientMeResponse(): ClientMeResponse {
        return ClientMeResponse(
            id = "mock-client-id",
            firstName = "Miloš",
            lastName = "Milošević",
            gender = Gender.Male,
            email = "scekic.veselin88novi@gmail.com",
            phone = "+1 555 1234567",
            address = "Dolje u Banji"
        )
    }

    fun getMockAccount(): AccountMock {
        return AccountMock(
            id = "account-id-001",
            accountNumber = "160-0000000444205-47",
            availableBalance = 2750582.94,
            currency = "USD",
            accountName = "My Company LLC",
            incomingPayments = 899887.68,
            outgoingPayments = 69000.00,
            allowedOverdraft = 0.00
        )
    }

    fun getMockTransactions(): List<TransactionMock> {
        return listOf(
            TransactionMock(
                id = "tx-001",
                date = "2025-03-15",
                description = "Payment from Client X",
                amount = 15000.00,
                currency = "USD"
            ),
            TransactionMock(
                id = "tx-002",
                date = "2025-03-14",
                description = "Payout to Supplier Y",
                amount = -8500.00,
                currency = "USD"
            ),
            TransactionMock(
                id = "tx-003",
                date = "2025-03-13",
                description = "Payment from Client Z",
                amount = 21000.00,
                currency = "USD"
            ),
            TransactionMock(
                id = "tx-004",
                date = "2025-03-12",
                description = "Purchase of materials",
                amount = -12300.50,
                currency = "USD"
            ),
            TransactionMock(
                id = "tx-005",
                date = "2025-03-10",
                description = "Monthly installment payment",
                amount = 20000.00,
                currency = "USD"
            )
        )
    }
}
