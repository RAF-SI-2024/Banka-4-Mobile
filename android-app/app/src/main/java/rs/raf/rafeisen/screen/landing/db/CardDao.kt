package rs.raf.rafeisen.screen.landing.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {

    @Upsert
    suspend fun upsertAll(cards: List<CardEntity>)

    @Query("SELECT * FROM CardEntity")
    fun observeAll(): Flow<List<CardEntity>>
}
