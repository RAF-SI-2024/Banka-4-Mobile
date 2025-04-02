package rs.raf.rafeisen.totp.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.turingcomplete.kotlinonetimepassword.GoogleAuthenticator
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import rs.raf.rafeisen.totp.home.TotpContract.UiState
import rs.raf.rafeisen.store.ActiveAccountStore
import rs.raf.rafeisen.totp.model.TotpUiModel
import rs.raf.rafeisen.totp.repository.TotpRepository
import java.util.Date
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class TotpViewModel @Inject constructor(
    private val totpRepository: TotpRepository,
    private val activeAccountStore: ActiveAccountStore,
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()
    private fun setState(reducer: UiState.() -> UiState) = _state.getAndUpdate { it.reducer() }

    private val jobs = mutableMapOf<String, Job>()


    init {
        observeTotpCodes()
    }

    private fun observeTotpCodes() =
        viewModelScope.launch {
            totpRepository
                .observeAllByUserId(userId = activeAccountStore.activeUserId())
                .apply { setState { copy(isLoading = true) } }
                .onEach { items ->
                    jobs.values.forEach { it.cancel() }
                    jobs.clear()

                    items.forEach { item ->
                        val job = viewModelScope.launch {
                            while (true) {
                                val timestamp = Date(System.currentTimeMillis())
                                val code = GoogleAuthenticator(item.secret.toByteArray())
                                    .generate(timestamp)

                                setState {
                                    copy(
                                        totpCodes = totpCodes + (item.secret to TotpUiModel(
                                            code = code,
                                            issuer = item.issuer,
                                        ))
                                    )
                                }

                                delay(1.seconds)

                            }
                        }

                        jobs[item.secret] = job
                    }
                }
                .apply { setState { copy(isLoading = false) } }
                .launchIn(viewModelScope)
        }

    private fun startTimer(secret: String) =
        viewModelScope.launch {
            Timer().schedule(object : TimerTask() {
                override fun run() {
                }
            }, 0, 1000)
        }

}
