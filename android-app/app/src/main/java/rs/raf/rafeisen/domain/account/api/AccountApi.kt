package rs.raf.rafeisen.domain.account.api

import retrofit2.http.GET
import rs.raf.rafeisen.domain.account.api.response.AccountResponse

interface AccountApi {

    @GET("/account")
    suspend fun getAllAccounts(): List<AccountResponse>
}
