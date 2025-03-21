package rs.raf.rafeisen.totp.repository

import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.withContext
import rs.raf.rafeisen.coroutines.CoroutineDispatcherProvider
import rs.raf.rafeisen.db.AppDatabase
import rs.raf.rafeisen.totp.model.Totp
import javax.inject.Inject

class TotpRepository @Inject constructor(
    private val database: AppDatabase,
    private val dispatchers: CoroutineDispatcherProvider,
) {
    suspend fun upsertTotp(totp: Totp) =
        withContext(dispatchers.io()) {
            database.totps().upsert(totp)
        }

    suspend fun observeAllByUserId(userId: String) =
        withContext(dispatchers.io()) {
            database.totps().observeAllByUserId(userId = userId)
        }.distinctUntilChanged()

    suspend fun getAllByUserId(userId: String) =
        withContext(dispatchers.io()) {
            database.totps().getAllByUserId(userId = userId)
        }
}
