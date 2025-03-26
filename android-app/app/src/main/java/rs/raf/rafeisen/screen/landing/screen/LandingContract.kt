package rs.raf.rafeisen.screen.landing.screen

import rs.raf.rafeisen.screen.landing.mock.AccountMock
import rs.raf.rafeisen.screen.landing.mock.TransactionMock
import rs.raf.rafeisen.screen.landing.data.CardDto

interface LandingContract {
    data class LandingUIState(
        val isLoading: Boolean = true,
        val error: String? = null,
        val accounts: List<AccountMock> = emptyList(),
        val transactions: List<TransactionMock> = emptyList(),
        val cards: List<CardDto> = emptyList()

    )

    sealed class LandingUIEvent {
        object LoadAccountAndCardsData : LandingUIEvent()
    }
}
