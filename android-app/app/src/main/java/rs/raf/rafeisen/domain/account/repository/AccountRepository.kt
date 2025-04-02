package rs.raf.rafeisen.domain.account.repository

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.withContext
import rs.raf.rafeisen.coroutines.CoroutineDispatcherProvider
import rs.raf.rafeisen.db.AppDatabase
import rs.raf.rafeisen.domain.account.api.AccountApi
import rs.raf.rafeisen.domain.account.model.Account
import rs.raf.rafeisen.mappers.toEntity

class AccountRepository @Inject constructor(
    private val api: AccountApi,
    private val database: AppDatabase,
    private val dispatchers: CoroutineDispatcherProvider,
) {

    suspend fun fetchAccounts() =
        withContext(dispatchers.io()) {
            val response = api.getAllAccounts()
            val entities = response.map { it.toEntity() }
            database.accounts().upsertAll(entities)
        }

    fun observeAccounts(): Flow<List<Account>> = database.accounts().observeAll().distinctUntilChanged()
}
