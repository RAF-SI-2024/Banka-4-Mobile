package rs.raf.rafeisen.screen.totp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import rs.raf.rafeisen.model.TotpSecret
import rs.raf.rafeisen.screen.totp.AddTotpContract.SideEffect
import rs.raf.rafeisen.screen.totp.AddTotpContract.UiEvent
import rs.raf.rafeisen.screen.totp.AddTotpContract.UiState
import rs.raf.rafeisen.store.ActiveAccountStore
import rs.raf.rafeisen.store.TotpSecretsStore
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class AddTotpViewModel @Inject constructor(
    private val totpSecretsStore: TotpSecretsStore,
    private val activeAccountStore: ActiveAccountStore
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
                    is UiEvent.AddTotpCode -> addTotpCode(issuer = it.issuer, secret = it.secret)
                }
            }

        }

    private fun addTotpCode(issuer: String, secret: String) {
        viewModelScope.launch {
            delay(5.seconds)
            val currentUserId = activeAccountStore.activeUserId()
            val totpSecret = TotpSecret(
                userId = currentUserId,
                issuer = issuer,
                secret = secret,
            )
            totpSecretsStore.addTotpSecret(totpSecret)

            setState { copy(isWorking = false) }
            setEffect(SideEffect.TotpCodeAddedSuccessfully)
        }
    }
}
