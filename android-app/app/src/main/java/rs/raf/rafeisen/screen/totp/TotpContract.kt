package rs.raf.rafeisen.screen.totp

interface TotpContract {
    data class TotpUiState(
        val issuer: String = "",
        val secret: String = "",
        val isSubmitting: Boolean = false,
        val errorMessage: String? = null,
        val success: Boolean = false
    )

    sealed class TotpUiEvent {
        data class OnIssuerChanged(val issuer: String) : TotpUiEvent()
        data class OnSecretChanged(val secret: String) : TotpUiEvent()
        object Submit : TotpUiEvent()
    }
}
