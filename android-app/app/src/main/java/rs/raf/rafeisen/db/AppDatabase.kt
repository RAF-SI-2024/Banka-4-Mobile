package rs.raf.rafeisen.db

import androidx.room.Database
import androidx.room.RoomDatabase
import rs.raf.rafeisen.totp.db.TotpDao
import rs.raf.rafeisen.totp.model.Totp

@Database(
    entities = [Totp::class],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun totps(): TotpDao
}
