package rs.raf.rafeisen.domain.user

import javax.inject.Inject
import javax.inject.Singleton
import rs.raf.rafeisen.domain.client.api.ClientApi
import rs.raf.rafeisen.store.AccountsStore
import rs.raf.rafeisen.store.ActiveAccountStore
import timber.log.Timber

@Singleton
class UserDataUpdater @Inject constructor(
    private val clientApi: ClientApi,
    private val accountsStore: AccountsStore,
    private val activeAccountStore: ActiveAccountStore,
) {
    suspend fun updateUserAccount() {
        Timber.d(" [UserDataUpdater] Starting user data update...")

        val activeUserId = activeAccountStore.activeUserId()
        Timber.d(" [UserDataUpdater] Active user ID: $activeUserId")

        try {
            val me = clientApi.getMe()
            Timber.d(" [UserDataUpdater] Received user data from API: $me")

            accountsStore.getAndUpdateAccount(activeUserId) {
                copy(
                    firstName = me.firstName,
                    lastName = me.lastName,
                    gender = me.gender,
                    email = me.email,
                    phone = me.phone,
                    address = me.address,
                )
            }

            Timber.d(" [UserDataUpdater] Successfully updated account info.")
        } catch (e: Exception) {
            Timber.e(e, " [UserDataUpdater] Failed to update user account.")
        }
    }

}
