package rs.raf.rafeisen.screen.landing.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Upsert
    suspend fun upsertAll(accounts: List<AccountEntity>)

    @Query("SELECT * FROM AccountEntity")
    fun observeAll(): Flow<List<AccountEntity>>

    @Query("SELECT accountNumber FROM AccountEntity")
    suspend fun getAllAccountNumbers(): List<String>

}
