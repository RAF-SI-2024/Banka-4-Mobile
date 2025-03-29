package rs.raf.rafeisen.screen.landing.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import rs.raf.rafeisen.screen.landing.repository.AccountRepository
import rs.raf.rafeisen.screen.landing.repository.CardRepository
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(
    private val cardRepository: CardRepository,
    private val accountRepository: AccountRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LandingContract.LandingUIState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<LandingContract.LandingUIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        observeEvents()
        observeCards()
        observeAccounts()
        sendEvent(LandingContract.LandingUIEvent.LoadAccountAndCardsData)
    }

    fun sendEvent(event: LandingContract.LandingUIEvent) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    private fun observeEvents() {
        viewModelScope.launch {
            eventFlow.collect { event ->
                when (event) {
                    is LandingContract.LandingUIEvent.LoadAccountAndCardsData -> loadData()
                }
            }
        }
    }

    private fun observeCards() {
        viewModelScope.launch {
            cardRepository.observeCards().collect { cards ->
                setState { copy(cards = cards) }
            }
        }
    }

    private fun observeAccounts() {
        viewModelScope.launch {
            accountRepository.observeAccounts().collect { accounts ->
                setState { copy(accounts = accounts) }
            }
        }
    }

    private fun setState(reducer: LandingContract.LandingUIState.() -> LandingContract.LandingUIState) {
        _state.update { it.reducer() }
    }

    private fun loadData() {
        viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }
            try {
                accountRepository.syncAccounts()

                cardRepository.syncCards()

                setState { copy(isLoading = false) }
            } catch (e: Exception) {
                e.printStackTrace()
                setState {
                    copy(isLoading = false, error = "Something went wrong. Please try again.")
                }
            }
        }
    }
}
