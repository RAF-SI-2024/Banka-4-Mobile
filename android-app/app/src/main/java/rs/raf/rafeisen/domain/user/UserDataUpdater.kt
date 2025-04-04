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


        val activeUserId = activeAccountStore.activeUserId()


        try {
            val me = clientApi.getMe()


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


        } catch (e: Exception) {
            Timber.e(e, " Failed to update user account.")
        }
    }

}
