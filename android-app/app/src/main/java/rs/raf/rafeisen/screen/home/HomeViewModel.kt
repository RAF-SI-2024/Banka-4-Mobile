package rs.raf.rafeisen.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import rs.raf.rafeisen.api.client.response.ClientMeResponse
import rs.raf.rafeisen.screen.home.mock.HomeMockData
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    //  (ClientApi, AccountApi...), sada koristimo mock
) : ViewModel() {

    private val _state = MutableStateFlow(HomeContract.HomeUiState(isLoading = true))
    val state = _state.asStateFlow()

    init {
        fetchData()
    }

    fun onEvent(event: HomeContract.HomeUiEvent) {
        when (event) {
            HomeContract.HomeUiEvent.FetchData -> fetchData()
        }
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                val client: ClientMeResponse = HomeMockData.getMockClientMeResponse()
                val account = HomeMockData.getMockAccount()
                val transactions = HomeMockData.getMockTransactions()

                _state.getAndUpdate {
                    it.copy(
                        clientName = "${client.firstName} ${client.lastName}",
                        email = client.email,
                        phone = client.phone,
                        address = client.address,

                        account = account,
                        transactions = transactions,

                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _state.getAndUpdate { current ->
                    current.copy(
                        isLoading = false,
                        error = e.message ?: "Unknown error"
                    )
                }
            }
        }
    }
}
