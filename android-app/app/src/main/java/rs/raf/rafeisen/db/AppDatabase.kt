package rs.raf.rafeisen.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rs.raf.rafeisen.db.converter.BigDecimalConverter
import rs.raf.rafeisen.db.converter.LocalDateConverter
import rs.raf.rafeisen.domain.account.model.Account
import rs.raf.rafeisen.domain.account.model.AccountDao
import rs.raf.rafeisen.domain.card.model.Card
import rs.raf.rafeisen.domain.card.model.CardDao
import rs.raf.rafeisen.totp.db.TotpDao
import rs.raf.rafeisen.totp.model.Totp

@Database(
    entities = [
        Totp::class,
        Card::class,
        Account::class,
    ],
    version = 2,
)
@TypeConverters(BigDecimalConverter::class, LocalDateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun totps(): TotpDao
    abstract fun cards(): CardDao
    abstract fun accounts(): AccountDao
}
