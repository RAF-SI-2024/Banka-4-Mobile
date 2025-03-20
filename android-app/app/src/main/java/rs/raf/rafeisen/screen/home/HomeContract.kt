package rs.raf.rafeisen.screen.home

import rs.raf.rafeisen.screen.home.mock.AccountMock
import rs.raf.rafeisen.screen.home.mock.TransactionMock

interface HomeContract {

    data class HomeUiState(
        val clientName: String = "",
        val email: String = "",
        val phone: String = "",
        val address: String = "",

        val account: AccountMock? = null,
        val transactions: List<TransactionMock> = emptyList(),

        val isLoading: Boolean = false,
        val error: String? = null
    )

    sealed class HomeUiEvent {
        object FetchData : HomeUiEvent()
    }
}
