package rs.raf.rafeisen.screen.login

interface LoginContract {
    data class UiState(
        val isWorking: Boolean = false,
    )

    sealed class UiEvent {
        data class LoginRequest(val email: String, val password: String) : UiEvent()
    }

    sealed class SideEffect {
        data object LoginSuccessful : SideEffect()
    }
}
