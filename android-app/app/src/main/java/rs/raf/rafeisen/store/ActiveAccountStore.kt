package rs.raf.rafeisen.store

import androidx.datastore.core.DataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import rs.raf.rafeisen.coroutines.CoroutineDispatcherProvider
import rs.raf.rafeisen.store.di.ActiveAccountDataStore
import javax.inject.Inject

class ActiveAccountStore
    @Inject
    constructor(
        dispatchers: CoroutineDispatcherProvider,
        accountsStore: AccountsStore,
        @ActiveAccountDataStore private val persistence: DataStore<String>,
    ) {
        private val scope = CoroutineScope(dispatchers.io())

        val activeUserId =
            persistence.data
                .stateIn(
                    scope = scope,
                    started = SharingStarted.Eagerly,
                    initialValue = runBlocking { persistence.data.first() },
                )

        fun activeUserId() = activeUserId.value

        @get:Throws(NoSuchElementException::class)
        val activeUserAccount =
            accountsStore.userAccounts
                .map { it.first { it.id == activeUserId() } }
                .distinctUntilChanged()

        @Throws(NoSuchElementException::class)
        suspend fun activeUserAccount() = activeUserAccount.first()

        suspend fun setActiveUserId(userId: String) {
            persistence.updateData { userId }
        }

        suspend fun clearActiveUserAccount() {
            persistence.updateData { "" }
        }
    }
