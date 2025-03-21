package rs.raf.rafeisen.totp.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import rs.raf.rafeisen.totp.model.Totp

@Dao
interface TotpDao {

    @Query("SELECT * FROM Totp WHERE userId = :userId")
    fun getAllByUserId(userId: String): List<Totp>

    @Upsert
    fun upsert(totp: Totp)
}
