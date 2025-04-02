package rs.raf.rafeisen.store

import androidx.datastore.core.DataStore
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking

@Singleton
class CredentialsStore
@Inject
constructor(private val persistence: DataStore<Set<Credential>>) {
    private val scope = CoroutineScope(Dispatchers.IO)

    val credentials =
        persistence.data
            .stateIn(
                scope = scope,
                started = SharingStarted.Eagerly,
                initialValue = runBlocking { persistence.data.first() },
            )

    fun findOrThrow(userId: String): Credential =
        credentials.value.find { it.id == userId }
            ?: throw IllegalArgumentException("Credential not found for $userId.")

    suspend fun updateAccessToken(userId: String, accessToken: String) =
        persistence.updateData { creds ->
            creds.find { it.id == userId }?.let { cred ->
                creds.filter { it.id != userId }.toSet() + cred.copy(accessToken = accessToken)
            } ?: creds
        }

    suspend fun addCredential(credential: Credential) = persistence.updateData { it + credential }

    suspend fun removeCredentialByUserId(userId: String) =
        credentials.value.firstOrNull { it.id == userId }?.let { cred ->
            persistence.updateData { it - cred }
        }

    suspend fun clearCredentials() = persistence.updateData { emptySet() }
}
