package rs.raf.rafeisen.api.account

import retrofit2.http.GET
import rs.raf.rafeisen.api.account.response.AccountResponse

interface AccountApi {

    @GET("/account")
    suspend fun getAllAccounts(): List<AccountResponse>
}
