package rs.raf.rafeisen.store

import androidx.datastore.core.DataStore
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import rs.raf.rafeisen.coroutines.CoroutineDispatcherProvider
import rs.raf.rafeisen.store.exception.NoSuchAccountException

@Singleton
class AccountsStore
@Inject
constructor(
    dispatchers: CoroutineDispatcherProvider,
    private val persistence: DataStore<List<UserAccount>>,
) {
    private val scope = CoroutineScope(dispatchers.io())

    private val writeMutex = Mutex()

    val userAccounts =
        persistence.data
            .stateIn(
                scope = scope,
                started = SharingStarted.Eagerly,
                initialValue = runBlocking { persistence.data.first() },
            )

    suspend fun getAndUpdateAccount(userId: String, reducer: UserAccount.() -> UserAccount): UserAccount =
        writeMutex.withLock {
            val current = findByIdOrNull(userId = userId) ?: throw NoSuchAccountException()
            val updated = current.reducer()
            updateOrInsertAccount(updated)
            updated
        }

    suspend fun upsertAccount(userAccount: UserAccount) =
        writeMutex.withLock {
            updateOrInsertAccount(userAccount)
        }

    suspend fun deleteAccount(userId: String) =
        writeMutex.withLock {
            persistence.updateData { accounts ->
                val accountIndex = accounts.indexOfFirst { it.id == userId }
                accounts.toMutableList().apply { removeAt(accountIndex) }
            }
        }

    fun findByIdOrNull(userId: String) = userAccounts.value.find { it.id == userId }

    private suspend fun updateOrInsertAccount(userAccount: UserAccount) {
        persistence.updateData { accounts ->
            val existingIndex = accounts.indexOfFirst { it.id == userAccount.id }
            accounts.toMutableList().apply {
                if (existingIndex != -1) removeAt(existingIndex)
                add(userAccount)
            }
        }
    }
}
