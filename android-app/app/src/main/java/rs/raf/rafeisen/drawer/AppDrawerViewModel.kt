package rs.raf.rafeisen.drawer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import rs.raf.rafeisen.drawer.AppDrawerContract.UiState
import rs.raf.rafeisen.store.ActiveAccountStore

@HiltViewModel
class AppDrawerViewModel @Inject constructor(private val activeAccountStore: ActiveAccountStore) : ViewModel() {
    private val _state = MutableStateFlow(
        UiState(
            menuItems = buildDrawerMenuItems(userId = activeAccountStore.activeUserId()),
        ),
    )
    val state = _state.asStateFlow()
    private fun setState(reducer: UiState.() -> UiState) = _state.getAndUpdate { it.reducer() }

    init {
        observeActiveAccount()
    }

    private fun observeActiveAccount() =
        viewModelScope.launch {
            activeAccountStore.activeUserAccount.collect {
                setState { copy(activeAccount = it) }
            }
        }

    private fun buildDrawerMenuItems(userId: String) =
        listOf(
            DrawerScreenDestination.Home,
            DrawerScreenDestination.Profile,
            DrawerScreenDestination.SignOut(userId = userId),
        )
}
