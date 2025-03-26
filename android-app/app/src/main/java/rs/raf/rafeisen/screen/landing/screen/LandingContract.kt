package rs.raf.rafeisen.screen.landing

import rs.raf.rafeisen.screen.home.mock.AccountMock
import rs.raf.rafeisen.screen.home.mock.TransactionMock

interface LandingContract {
    data class LandingUIState(
        val isLoading: Boolean = true,
        val error: String? = null,
        val account: AccountMock? = null,
        val transactions: List<TransactionMock> = emptyList()
    )

    sealed class LandingUIEvent {
        object LoadAccountAndCardsData : LandingUIEvent()
    }
}
