package rs.raf.rafeisen.screen.landing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import rs.raf.rafeisen.screen.home.mock.HomeMockData

class LandingViewModel : ViewModel() {

    private val _state = MutableStateFlow(LandingContract.LandingState())
    val state = _state.asStateFlow()

    init {
        fetchData()
    }

    fun onEvent(event: LandingContract.LandingEvent) {
        when (event) {
            LandingContract.LandingEvent.FetchData -> fetchData()
            else -> {}
        }
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                delay(500L) // Simulacija čekanja
                val account = HomeMockData.getMockAccount()
                val transactions = HomeMockData.getMockTransactions()
                _state.value = LandingContract.LandingState(
                    isLoading = false,
                    account = account,
                    transactions = transactions
                )
            } catch (e: Exception) {
                _state.value = LandingContract.LandingState(
                    isLoading = false,
                    error = e.message ?: "Unknown error"
                )
            }
        }
    }
}
