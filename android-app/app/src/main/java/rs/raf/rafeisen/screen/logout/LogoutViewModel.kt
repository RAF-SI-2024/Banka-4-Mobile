package rs.raf.rafeisen.screen.logout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import rs.raf.rafeisen.auth.AuthService
import rs.raf.rafeisen.screen.logout.LogoutContract.SideEffect
import rs.raf.rafeisen.screen.logout.LogoutContract.UiEvent

@HiltViewModel
class LogoutViewModel @Inject constructor(private val authService: AuthService) : ViewModel() {

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
                    UiEvent.LogoutConfirmed -> logout()
                }
            }
        }

    private fun logout() =
        viewModelScope.launch {
            runCatching {
                authService.logout()
            }.onSuccess {
                setEffect(SideEffect.LogoutSuccessful)
            }.onFailure {
                /* TODO: should we do something? not sure. don't care. it's 4am. */
            }
        }
}
