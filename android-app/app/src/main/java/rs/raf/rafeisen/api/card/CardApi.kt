package rs.raf.rafeisen.api.card

import retrofit2.http.GET
import retrofit2.http.Query
import rs.raf.rafeisen.api.card.response.CardPageResponse

interface CardsApi {
    @GET("/cards/client/search")
    suspend fun getCardsByAccountNumber(
        @Query("accountNumber") accountNumber: String,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20,
    ): CardPageResponse
}
