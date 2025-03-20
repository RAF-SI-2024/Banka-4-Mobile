package rs.raf.rafeisen.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import rs.raf.rafeisen.auth.AuthService
import rs.raf.rafeisen.screen.login.LoginContract.SideEffect
import rs.raf.rafeisen.screen.login.LoginContract.UiEvent
import rs.raf.rafeisen.screen.login.LoginContract.UiState
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authService: AuthService,
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()
    private fun setState(reducer: UiState.() -> UiState) = _state.getAndUpdate { it.reducer() }

    private val events: MutableSharedFlow<UiEvent> = MutableSharedFlow()
    fun setEvent(event: UiEvent) = viewModelScope.launch { events.emit(event) }

    private val _effects = Channel<SideEffect>()
    val effects = _effects.receiveAsFlow()
    private fun setEffect(effect: SideEffect) = viewModelScope.launch { _effects.send(effect) }

    init {
        observeEvents()
    }

    private fun observeEvents() =
        viewModelScope.launch {
            events.collect {
                when (it) {
                    is UiEvent.LoginRequest -> login(it.email, it.password)
                }
            }
        }

    private fun login(email: String, password: String) =
        viewModelScope.launch {
            try {
                setState { copy(isWorking = true) }
                authService.login(email = email, password = password)
                setEffect(SideEffect.LoginSuccessful)
            } catch (error: Exception) {
                /* TODO: implement better error handling logic */
                Timber.w(error)
            } finally {
                setState { copy(isWorking = false) }
            }
        }
}
