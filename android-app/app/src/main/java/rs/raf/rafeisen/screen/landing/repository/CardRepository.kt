package rs.raf.rafeisen.screen.landing.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import rs.raf.rafeisen.api.card.CardsApi
import rs.raf.rafeisen.coroutines.CoroutineDispatcherProvider
import rs.raf.rafeisen.db.AppDatabase
import rs.raf.rafeisen.screen.landing.mapping.toEntity
import rs.raf.rafeisen.screen.landing.mapping.toUiModel
import rs.raf.rafeisen.screen.landing.model.CardUIModel
import javax.inject.Inject

class CardRepository @Inject constructor(
    private val database: AppDatabase,
    private val api: CardsApi,
    private val dispatchers: CoroutineDispatcherProvider,
    private val accountRepository: AccountRepository,
) {

    suspend fun syncCards() = withContext(dispatchers.io()) {
        val accountNumbers = accountRepository.getAllAccountNumbers()


        val allCards = accountNumbers.flatMap { accountNumber ->
            try {

                val cards = api.getCardsByAccountNumber(accountNumber).content
                cards
            } catch (e: Exception) {
                emptyList()
            }
        }


        val entities = allCards.map { it.toEntity() }
        database.cards().upsertAll(entities)
    }


    fun observeCards(): Flow<List<CardUIModel>> {
        return database.cards().observeAll().map { list ->
            list.map { it.toUiModel() }
        }
    }
}
