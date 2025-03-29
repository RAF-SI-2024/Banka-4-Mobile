package rs.raf.rafeisen.screen.landing.db

import rs.raf.rafeisen.db.AppDatabase
import javax.inject.Inject

class UserDataCleaner @Inject constructor(
    private val database: AppDatabase
) {
    suspend fun clearUserData() {
        database.accounts().deleteAll()
        database.cards().deleteAll()

    }
}
