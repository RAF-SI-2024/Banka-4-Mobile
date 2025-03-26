package rs.raf.rafeisen.screen.landing.mock


data class AccountMock(
    val id: String,
    val accountNumber: String,
    val balance: Double,
    val availableBalance: Double,
    val accountMaintenance: Double,
    val createdDate: String,
    val expirationDate: String,
    val active: Boolean,
    val accountType: String,
    val dailyLimit: Double,
    val monthlyLimit: Double,
    val currency: CurrencyMock,
    val employee: EmployeeMock,
    val client: ClientMock,
    val company: CompanyMock
)

data class CurrencyMock(
    val id: String,
    val name: String,
    val symbol: String,
    val description: String,
    val active: Boolean,
    val code: String
)

data class EmployeeMock(
    val id: String,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: String,
    val gender: String,
    val email: String,
    val phone: String,
    val address: String,
    val username: String,
    val position: String,
    val department: String,
    val active: Boolean
)

data class ClientMock(
    val id: String,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: String,
    val gender: String,
    val email: String,
    val phone: String,
    val address: String,
    val privileges: List<String>,
    val has2FA: Boolean
)

data class CompanyMock(
    val id: String,
    val name: String,
    val tin: String,
    val crn: String,
    val address: String,
    val activityCode: String
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
            id = "11111111-2222-3333-4444-555555555555",
            accountNumber = "1234567890",
            balance = 1000.0,
            availableBalance = 800.0,
            accountMaintenance = 100.0,
            createdDate = "2023-01-01",
            expirationDate = "2028-01-01",
            active = true,
            accountType = "CheckingPersonal",
            dailyLimit = 100.0,
            monthlyLimit = 1000.0,
            currency = CurrencyMock(
                id = "11111111-2222-3333-4444-555555555555",
                name = "Serbian Dinar",
                symbol = "RSD",
                description = "Serbian Dinar currency",
                active = true,
                code = "RSD"
            ),
            employee = EmployeeMock(
                id = "1de54a3a-d879-4154-aa3a-e40598186f93",
                firstName = "Ognjen",
                lastName = "Jukic",
                dateOfBirth = "1990-05-15",
                gender = "MALE",
                email = "mljubic9422112rn@raf.rs",
                phone = "+381610123456",
                address = "123",
                username = "funfa2c1t",
                position = "Software Engineer",
                department = "IT",
                active = true
            ),
            client = ClientMock(
                id = "1fad2c01-f82f-41a6-822c-8ca1b3232575",
                firstName = "Mehmedalija",
                lastName = "Doe",
                dateOfBirth = "1990-01-01",
                gender = "MALE",
                email = "danny.jo@example.com",
                phone = "+381684523697",
                address = "123",
                privileges = listOf("CAN_TRADE"),
                has2FA = false
            ),
            company = CompanyMock(
                id = "cccccccc-4444-dddd-5555-eeee6666ffff",
                name = "Acme Corp",
                tin = "123456789",
                crn = "987654321",
                address = "123",
                activityCode = "441100"
            )
        )
    }

    fun getMockTransactions(): List<TransactionMock> {
        return listOf(
            TransactionMock("tx-001", "2025-03-15", "Uplata od klijenta X", 15000.0, "RSD"),
            TransactionMock("tx-002", "2025-03-14", "Isplata za dobavljača Y", -8500.0, "RSD"),
            TransactionMock("tx-003", "2025-03-13", "Uplata od klijenta Z", 21000.0, "RSD"),
            TransactionMock("tx-004", "2025-03-12", "Kupovina materijala", -12300.5, "RSD"),
            TransactionMock("tx-005", "2025-03-10", "Uplata - mesečna rata", 20000.0, "RSD")
        )
    }
}
