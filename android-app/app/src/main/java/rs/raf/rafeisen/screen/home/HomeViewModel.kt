package rs.raf.rafeisen.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import rs.raf.rafeisen.domain.account.repository.AccountRepository
import rs.raf.rafeisen.domain.card.repository.CardRepository
import rs.raf.rafeisen.domain.user.UserDataUpdater
import rs.raf.rafeisen.mappers.toUiModel
import rs.raf.rafeisen.screen.home.HomeContract.UiEvent
import rs.raf.rafeisen.screen.home.HomeContract.UiState
import timber.log.Timber

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val cardRepository: CardRepository,
    private val accountRepository: AccountRepository,
    private val userDataUpdater: UserDataUpdater
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()
    private fun setState(reducer: UiState.() -> UiState) = _state.getAndUpdate { it.reducer() }

    private val events: MutableSharedFlow<UiEvent> = MutableSharedFlow()
    fun setEvent(event: UiEvent) = viewModelScope.launch { events.emit(event) }

    init {
        observeUserData()
        observeEvents()
        fetchCards()
        fetchAccounts()
        observeCards()
        observeAccounts()
    }

    private fun observeEvents() {
        viewModelScope.launch {
            events.collect { event ->
                when (event) {
                    is UiEvent.LoadAccountAndCardsData -> {
                        fetchCards()
                        fetchAccounts()
                    }
                }
            }
        }
    }

    private fun observeCards() {
        viewModelScope.launch {
            cardRepository.observeCards().collect { cards ->
                setState { copy(cards = cards.map { it.toUiModel() }) }
            }
        }
    }

    private fun observeAccounts() {
        viewModelScope.launch {
            accountRepository.observeAccounts().collect { accounts ->
                setState { copy(accounts = accounts.map { it.toUiModel() }) }
            }
        }
    }

    private fun fetchCards() =
        viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }
            try {
                cardRepository.fetchCards()
            } catch (e: Exception) {
                Timber.w(e)
                setState { copy(error = e) }
            } finally {
                setState { copy(isLoading = false) }
            }
        }

    private fun fetchAccounts() {
        viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }
            try {
                accountRepository.fetchAccounts()
            } catch (e: Exception) {
                Timber.w(e)
                setState { copy(error = e) }
            } finally {
                setState { copy(isLoading = false) }
            }
        }
    }

    private fun observeUserData(){
        viewModelScope.launch {
            userDataUpdater.updateUserAccount()
        }
    }
}
