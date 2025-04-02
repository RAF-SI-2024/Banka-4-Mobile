package rs.raf.rafeisen.domain.card.model

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {
    @Upsert
    suspend fun upsertAll(cards: List<Card>)

    @Query("SELECT * FROM Card")
    fun observeAll(): Flow<List<Card>>
}
