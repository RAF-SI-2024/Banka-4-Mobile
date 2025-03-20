package rs.raf.rafeisen.screen.login

interface LoginContract {
    sealed class UiEvent {
        data class LoginRequest(val email: String, val password: String) : UiEvent()
    }

    sealed class SideEffect {
        data object LoginSuccessful : SideEffect()
    }
}
