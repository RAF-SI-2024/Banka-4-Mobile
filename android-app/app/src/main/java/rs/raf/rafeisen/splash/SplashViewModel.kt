package rs.raf.rafeisen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import rs.raf.rafeisen.store.ActiveAccountStore

@HiltViewModel
class SplashViewModel @Inject constructor(private val activeAccountStore: ActiveAccountStore) : ViewModel() {

    private val _isAuthCheckComplete = MutableStateFlow(false)
    val isAuthCheckComplete = _isAuthCheckComplete

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn = _isLoggedIn

    init {
        checkAuthState()
    }

    private fun checkAuthState() =
        viewModelScope.launch {
            _isLoggedIn.value = activeAccountStore.activeUserId().isNotEmpty()
            _isAuthCheckComplete.value = true
        }
}
