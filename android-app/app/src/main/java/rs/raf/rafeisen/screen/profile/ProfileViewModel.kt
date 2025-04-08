package rs.raf.rafeisen.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import rs.raf.rafeisen.domain.user.UserDataUpdater
import rs.raf.rafeisen.mappers.toProfileUIModel
import rs.raf.rafeisen.screen.profile.ProfileContract.UiEvent
import rs.raf.rafeisen.screen.profile.ProfileContract.UiState
import rs.raf.rafeisen.store.ActiveAccountStore
import rs.raf.rafeisen.store.UserAccount

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userDataUpdater: UserDataUpdater,
    private val activeAccountStore: ActiveAccountStore,
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()
    private fun setState(reducer: UiState.() -> UiState) = _state.getAndUpdate { it.reducer() }

    private val events = MutableSharedFlow<UiEvent>()
    fun setEvent(event: UiEvent) = viewModelScope.launch { events.emit(event) }

    init {
        updateUser()
        observeAccount()
        observeEvents()
    }

    private fun updateUser() {
        viewModelScope.launch {
            userDataUpdater.updateUserAccount()
        }
    }

    private fun observeAccount() {
        viewModelScope.launch {
            activeAccountStore.activeUserAccount.collect { user ->
                if (user != UserAccount.EMPTY) {
                    val uiModel = user.toProfileUIModel()
                    setState {
                        copy(
                            profile = uiModel,
                            isLoading = false,
                        )
                    }
                }
            }
        }
    }

    private fun observeEvents() {
        viewModelScope.launch {
            events.collect { event ->
                when (event) {
                    UiEvent.LoadProfileData -> updateUser()
                }
            }
        }
    }
}
