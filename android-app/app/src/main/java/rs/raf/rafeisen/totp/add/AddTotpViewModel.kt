package rs.raf.rafeisen.totp.add

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import rs.raf.rafeisen.navigation.issuerOrNull
import rs.raf.rafeisen.navigation.secretOrNull
import rs.raf.rafeisen.store.ActiveAccountStore
import rs.raf.rafeisen.totp.add.AddTotpContract.SideEffect
import rs.raf.rafeisen.totp.add.AddTotpContract.UiEvent
import rs.raf.rafeisen.totp.add.AddTotpContract.UiState
import rs.raf.rafeisen.totp.model.Totp
import rs.raf.rafeisen.totp.repository.TotpRepository

@HiltViewModel
class AddTotpViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val totpRepository: TotpRepository,
    private val activeAccountStore: ActiveAccountStore,
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
        tryAddTotp()
        observeEvents()
    }

    private fun tryAddTotp() =
        viewModelScope.launch {
            val secret = savedStateHandle.secretOrNull
            val issuer = savedStateHandle.issuerOrNull

            if (secret != null && issuer != null) {
                addTotpCode(issuer = issuer, secret = secret)
            }
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
            setState { copy(isWorking = true) }
            val currentUserId = activeAccountStore.activeUserId()
            val totpSecret = Totp(
                userId = currentUserId,
                issuer = issuer,
                secret = secret,
            )
            totpRepository.upsertTotp(totp = totpSecret)

            setState { copy(isWorking = false) }
            setEffect(SideEffect.TotpCodeAddedSuccessfully)
        }
    }
}
