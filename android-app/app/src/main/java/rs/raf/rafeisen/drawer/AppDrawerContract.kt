package rs.raf.rafeisen.drawer

import rs.raf.rafeisen.store.UserAccount

interface AppDrawerContract {
    data class UiState(
        val activeAccount: UserAccount = UserAccount.EMPTY,
        val menuItems: List<DrawerScreenDestination>,
    )
}
