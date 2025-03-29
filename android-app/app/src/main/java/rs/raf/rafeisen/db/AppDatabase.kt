package rs.raf.rafeisen.db

import androidx.room.Database
import androidx.room.RoomDatabase
import rs.raf.rafeisen.screen.landing.db.AccountDao
import rs.raf.rafeisen.screen.landing.db.AccountEntity
import rs.raf.rafeisen.screen.landing.db.CardDao
import rs.raf.rafeisen.screen.landing.db.CardEntity
import rs.raf.rafeisen.totp.db.TotpDao
import rs.raf.rafeisen.totp.model.Totp

@Database(
    entities = [Totp::class,
        CardEntity::class,
        AccountEntity::class],
    version = 3,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun totps(): TotpDao
    abstract fun cards(): CardDao
    abstract fun accounts(): AccountDao
}
