package rs.raf.rafeisen.screen.landing.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AccountEntity(
    @PrimaryKey val id: String,
    val accountNumber: String,
    val availableBalance: Double,
    val createdDate: String,
    val expirationDate: String,
    val accountType: String,
    val currencyCode: String,
    val clientFirstName: String,
    val clientLastName: String
)
