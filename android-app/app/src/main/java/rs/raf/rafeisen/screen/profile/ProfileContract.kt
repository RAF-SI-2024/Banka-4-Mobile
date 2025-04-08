package rs.raf.rafeisen.screen.profile

import rs.raf.rafeisen.screen.profile.model.ProfileUIModel

interface ProfileContract {

    data class UiState(
        val isLoading: Boolean = true,
        val profile: ProfileUIModel? = null,
        val error: Throwable? = null,
    )

    sealed class UiEvent {
        object LoadProfileData : UiEvent()
    }
}
