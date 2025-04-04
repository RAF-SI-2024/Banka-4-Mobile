package rs.raf.rafeisen.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import rs.raf.rafeisen.domain.user.repository.UserRepository
import rs.raf.rafeisen.mappers.toUiModel
import rs.raf.rafeisen.screen.profile.ProfileContract.UiEvent
import rs.raf.rafeisen.screen.profile.ProfileContract.UiState

@HiltViewModel
class ProfileViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()
    private fun setState(reducer: UiState.() -> UiState) = _state.getAndUpdate { it.reducer() }

    private val events = MutableSharedFlow<UiEvent>()
    fun setEvent(event: UiEvent) = viewModelScope.launch { events.emit(event) }

    init {
        observeEvents()
        loadProfile()
    }

    private fun observeEvents() {
        viewModelScope.launch {
            events.collect { event ->
                when (event) {
                    UiEvent.LoadProfileData -> loadProfile()
                }
            }
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }

            try {
                val client = userRepository.getCurrentUser()
                val profile = client.toUiModel()
                setState { copy(profile = profile) }
            } catch (e: Exception) {
                setState { copy(error = e) }
            } finally {
                setState { copy(isLoading = false) }
            }
        }
    }
}
