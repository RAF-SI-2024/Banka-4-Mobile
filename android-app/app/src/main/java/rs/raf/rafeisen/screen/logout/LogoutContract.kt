package rs.raf.rafeisen.screen.logout

interface LogoutContract {
    sealed class UiEvent {
        data object LogoutConfirmed : UiEvent()
    }

    sealed class SideEffect {
        data object LogoutSuccessful : SideEffect()
    }
}
