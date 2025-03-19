package rs.raf.rafeisen.db

import androidx.room.Database
import androidx.room.RoomDatabase
import rs.raf.rafeisen.model.Client

@Database(
    entities = [Client::class],
    version = 1,
)
abstract class AppDatabase : RoomDatabase()
