package rs.raf.rafeisen.domain.account.model

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Upsert
    suspend fun upsertAll(accounts: List<Account>)

    @Query("SELECT * FROM Account")
    fun observeAll(): Flow<List<Account>>
}
