package rs.raf.rafeisen.drawer

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun AppDrawer(
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    onDrawerDestinationClick: (DrawerScreenDestination) -> Unit, viewModel: AppDrawerViewModel = hiltViewModel(),
    content: @Composable () -> Unit,
) {
    val uiScope = rememberCoroutineScope()

    val uiState = viewModel.state.collectAsState()

    BackHandler(enabled = drawerState.isOpen) {
        uiScope.launch { drawerState.close() }
    }

    AppDrawer(
        drawerState = drawerState,
        onDrawerDestinationClick = {
            uiScope.launch { drawerState.close() }
            onDrawerDestinationClick(it)
        },
        content = content,
        state = uiState.value,
    )
}

@Composable
private fun AppDrawer(
    drawerState: DrawerState,
    state: AppDrawerContract.UiState,
    onDrawerDestinationClick: (DrawerScreenDestination) -> Unit,
    content: @Composable () -> Unit,
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier.padding(16.dp),
                ) {
                    Text(
                        text = state.activeAccount.fullName(),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Text(
                        text = state.activeAccount.email,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }

                HorizontalDivider()

                NavigationDrawerItems(
                    menuItems = state.menuItems,
                    onDrawerDestinationClick = onDrawerDestinationClick,
                )
            }
        },
        content = content,
    )
}

@Composable
fun NavigationDrawerItems(
    menuItems: List<DrawerScreenDestination>,
    onDrawerDestinationClick: (DrawerScreenDestination) -> Unit,
) {
    menuItems.map {
        NavigationDrawerItem(
            label = { Text(text = it.label()) },
            selected = false,
            onClick = { onDrawerDestinationClick(it) },
        )
    }
}

sealed class DrawerScreenDestination {
    data object Home : DrawerScreenDestination()
    data class SignOut(val userId: String) : DrawerScreenDestination()
}

private fun DrawerScreenDestination.label(): String =
    when (this) {
        DrawerScreenDestination.Home -> "Home"
        is DrawerScreenDestination.SignOut -> "Sign out"
    }
