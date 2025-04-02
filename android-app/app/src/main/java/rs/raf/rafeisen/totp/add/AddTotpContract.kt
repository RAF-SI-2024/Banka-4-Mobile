package rs.raf.rafeisen.totp.add

interface AddTotpContract {
    data class UiState(val isWorking: Boolean = false)

    sealed class UiEvent {
        data class AddTotpCode(val issuer: String, val secret: String) : UiEvent()
    }

    sealed class SideEffect {
        data object TotpCodeAddedSuccessfully : SideEffect()
    }
}
