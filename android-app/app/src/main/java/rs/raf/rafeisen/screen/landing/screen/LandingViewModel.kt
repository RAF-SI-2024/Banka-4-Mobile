package rs.raf.rafeisen.screen.landing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import rs.raf.rafeisen.screen.home.mock.HomeMockData

class LandingViewModel : ViewModel() {

    private val _state = MutableStateFlow(LandingContract.LandingUIState())
    val state = _state.asStateFlow()

    init {
        fetchData()
    }

    fun onEvent(event: LandingContract.LandingUIEvent) {
        when (event) {
            LandingContract.LandingUIEvent.LoadAccountAndCardsData -> fetchData()
            else -> {}
        }
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                delay(500L)
                val account = HomeMockData.getMockAccount()
                val transactions = HomeMockData.getMockTransactions()
                _state.value = LandingContract.LandingUIState(
                    isLoading = false,
                    account = account,
                    transactions = transactions
                )
            } catch (e: Exception) {
                _state.value = LandingContract.LandingUIState(
                    isLoading = false,
                    error = e.message ?: "Unknown error"
                )
            }
        }
    }
}
