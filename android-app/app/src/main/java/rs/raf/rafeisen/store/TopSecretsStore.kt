package rs.raf.rafeisen.store

import androidx.datastore.core.DataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import rs.raf.rafeisen.model.TotpSecret
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TotpSecretsStore @Inject constructor(
    private val persistence: DataStore<List<TotpSecret>>
) {
    private val scope = CoroutineScope(Dispatchers.IO)

    val totpSecrets =
        persistence.data
            .stateIn(
                scope = scope,
                started = SharingStarted.Eagerly,
                initialValue = runBlocking { persistence.data.first() }
            )

    suspend fun addTotpSecret(totpSecret: TotpSecret) {
        persistence.updateData { current ->
            current + totpSecret
        }
    }
}
