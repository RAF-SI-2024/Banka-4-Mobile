package rs.raf.rafeisen.screen.totp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import rs.raf.rafeisen.model.TotpSecret
import rs.raf.rafeisen.store.ActiveAccountStore
import rs.raf.rafeisen.store.TotpSecretsStore
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class TotpViewModel @Inject constructor(
    private val totpSecretsStore: TotpSecretsStore,
    private val activeAccountStore: ActiveAccountStore
) : ViewModel() {

    private val _state = MutableStateFlow(TotpContract.TotpUiState())
    val state = _state.asStateFlow()

    private fun setState(reducer: TotpContract.TotpUiState.() -> TotpContract.TotpUiState) {
        _state.getAndUpdate(reducer)
    }

    fun onEvent(event: TotpContract.TotpUiEvent) {
        when (event) {
            is TotpContract.TotpUiEvent.OnIssuerChanged -> {
                setState { copy(issuer = event.issuer, errorMessage = null) }
            }
            is TotpContract.TotpUiEvent.OnSecretChanged -> {
                setState { copy(secret = event.secret, errorMessage = null) }
            }
            TotpContract.TotpUiEvent.Submit -> {
                submitTotp()
            }
        }
    }

    private fun submitTotp() {
        viewModelScope.launch {
            setState { copy(isSubmitting = true, errorMessage = null, success = false) }
            try {
                val currentState = state.value
                if (currentState.issuer.isBlank() || currentState.secret.isBlank()) {
                    setState { copy(errorMessage = "Issuer and Secret must not be empty", isSubmitting = false) }
                    return@launch
                }
                val currentUserId = activeAccountStore.activeUserId()
                val totpSecret = TotpSecret(
                    id = UUID.randomUUID().toString(),
                    userId = currentUserId,
                    issuer = currentState.issuer,
                    secret = currentState.secret
                )
                totpSecretsStore.addTotpSecret(totpSecret)
                setState { copy(success = true, isSubmitting = false) }
            } catch (e: Exception) {
                setState { copy(errorMessage = e.message ?: "Unknown error", isSubmitting = false) }
            }
        }
    }
}
