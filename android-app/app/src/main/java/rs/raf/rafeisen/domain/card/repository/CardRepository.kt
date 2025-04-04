package rs.raf.rafeisen.domain.card.repository

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.withContext
import rs.raf.rafeisen.coroutines.CoroutineDispatcherProvider
import rs.raf.rafeisen.db.AppDatabase
import rs.raf.rafeisen.domain.card.api.CardsApi
import rs.raf.rafeisen.domain.card.model.Card
import rs.raf.rafeisen.mappers.toEntity

class CardRepository @Inject constructor(
    private val database: AppDatabase,
    private val api: CardsApi,
    private val dispatchers: CoroutineDispatcherProvider,
) {

    suspend fun fetchCards() =
        withContext(dispatchers.io()) {
            val cards = api.getAllCards(page = 0, size = 50)

            val entities = cards.content.map { it.toEntity() }
            database.cards().upsertAll(entities)
        }

    fun observeCards(): Flow<List<Card>> = database.cards().observeAll().distinctUntilChanged()
}
