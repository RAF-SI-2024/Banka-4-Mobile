package rs.raf.rafeisen.screen.home

import rs.raf.rafeisen.screen.home.model.AccountUIModel
import rs.raf.rafeisen.screen.home.model.CardUIModel

interface HomeContract {
    data class UiState(
        val isLoading: Boolean = true,
        val accounts: List<AccountUIModel> = emptyList(),
        val cards: List<CardUIModel> = emptyList(),
        val error: Throwable? = null,
    )

    sealed class UiEvent {
        object LoadAccountAndCardsData : UiEvent()
    }
}
