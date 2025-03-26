package rs.raf.rafeisen.screen.landing.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import rs.raf.rafeisen.screen.landing.mock.HomeMockData
import rs.raf.rafeisen.screen.landing.mock.mockCards
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(LandingContract.LandingUIState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<LandingContract.LandingUIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        observeEvents()
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
                    is LandingContract.LandingUIEvent.LoadAccountAndCardsData -> loadAccountAndCardsData()
                    else -> {}
                }

            }
        }
    }

    private fun setState(reducer: LandingContract.LandingUIState.() -> LandingContract.LandingUIState) {
        _state.update { it.reducer() }
    }

    private fun loadAccountAndCardsData() {
        viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }
            try {
                delay(500L)
                val account = HomeMockData.getMockAccount()
                val cards = mockCards
                val transactions = HomeMockData.getMockTransactions()
                setState {
                    copy(
                        isLoading = false,
                        accounts = listOf(account),
                        transactions = transactions,
                        cards = cards,
                        error = null
                    )
                }
            } catch (e: Exception) {
                setState {
                    copy(
                        isLoading = false,
                        error = "Something went wrong. Please try again."
                    )
                }
            }
        }
    }
}
