package rs.raf.rafeisen.screen.home.mock



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
    val date: String,
    val description: String,
    val amount: Double,
    val currency: String
)

object HomeMockData {



    fun getMockAccount(): AccountMock {
        return AccountMock(
            id = "account-id-001",
            accountNumber = "160-0000000444205-47",
            availableBalance = 2750582.94,
            currency = "RSD",
            accountName = "Moja Firma DOO",
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
                description = "Uplata od klijenta X",
                amount = 15000.00,
                currency = "RSD"
            ),
            TransactionMock(
                id = "tx-002",
                date = "2025-03-14",
                description = "Isplata za dobavljača Y",
                amount = -8500.00,
                currency = "RSD"
            ),
            TransactionMock(
                id = "tx-003",
                date = "2025-03-13",
                description = "Uplata od klijenta Z",
                amount = 21000.00,
                currency = "RSD"
            ),
            TransactionMock(
                id = "tx-004",
                date = "2025-03-12",
                description = "Kupovina materijala",
                amount = -12300.50,
                currency = "RSD"
            ),
            TransactionMock(
                id = "tx-005",
                date = "2025-03-10",
                description = "Uplata - mesečna rata",
                amount = 20000.00,
                currency = "RSD"
            )
        )
    }
}
