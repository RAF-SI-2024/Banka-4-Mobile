package rs.raf.rafeisen.domain.card.api

import retrofit2.http.GET
import retrofit2.http.Query
import rs.raf.rafeisen.domain.card.api.response.CardResponse
import rs.raf.rafeisen.utils.Pageable

interface CardsApi {
    @GET("/cards/client/search")
    suspend fun getCardsByAccountNumber(
        @Query("accountNumber") accountNumber: String,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20,
    ): Pageable<CardResponse>

    @GET("/cards/client/search")
    suspend fun getAllCards(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20,
    ): Pageable<CardResponse>
}
