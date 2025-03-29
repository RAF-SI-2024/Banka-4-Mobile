package rs.raf.rafeisen.screen.landing.screen


import rs.raf.rafeisen.screen.landing.model.AccountUIModel
import rs.raf.rafeisen.screen.landing.model.CardUIModel

interface LandingContract {
    data class LandingUIState(
        val isLoading: Boolean = true,
        val error: String? = null,
        val accounts: List<AccountUIModel> = emptyList(),
        val cards: List<CardUIModel> = emptyList()


    )

    sealed class LandingUIEvent {
        object LoadAccountAndCardsData : LandingUIEvent()
    }
}
