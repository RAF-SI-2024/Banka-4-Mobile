package rs.raf.rafeisen.screen.landing.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import rs.raf.rafeisen.api.account.AccountApi
import rs.raf.rafeisen.coroutines.CoroutineDispatcherProvider
import rs.raf.rafeisen.db.AppDatabase
import rs.raf.rafeisen.screen.landing.mapping.toEntity
import rs.raf.rafeisen.screen.landing.mapping.toUiModel
import rs.raf.rafeisen.screen.landing.model.AccountUIModel
import javax.inject.Inject

class AccountRepository @Inject constructor(
    private val api: AccountApi,
    private val database: AppDatabase,
    private val dispatchers: CoroutineDispatcherProvider,
) {

    suspend fun syncAccounts() = withContext(dispatchers.io()) {
        val response = api.getAllAccounts()
        val entities = response.map { it.toEntity() }
        database.accounts().upsertAll(entities)
    }

    fun observeAccounts(): Flow<List<AccountUIModel>> {
        return database.accounts().observeAll().map { list ->
            list.map { it.toUiModel() }
        }
    }

    suspend fun getAllAccountNumbers(): List<String> = withContext(dispatchers.io()) {
        database.accounts().getAllAccountNumbers()
    }
}
