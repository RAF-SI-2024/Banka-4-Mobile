package rs.raf.rafeisen.drawer

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import rs.raf.rafeisen.screen.profile.ui.InitialsAvatar

@Composable
fun AppDrawer(
    onDrawerDestinationClick: (DrawerScreenDestination) -> Unit,
    modifier: Modifier = Modifier,
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    viewModel: AppDrawerViewModel = hiltViewModel(),
    content: @Composable () -> Unit,
) {
    val uiScope = rememberCoroutineScope()
    val uiState = viewModel.state.collectAsState()

    BackHandler(enabled = drawerState.isOpen) {
        uiScope.launch { drawerState.close() }
    }

    AppDrawer(
        drawerState = drawerState,
        state = uiState.value,
        onDrawerDestinationClick = {
            uiScope.launch { drawerState.close() }
            onDrawerDestinationClick(it)
        },
        modifier = modifier,
        content = content,
    )
}

@Composable
private fun AppDrawer(
    drawerState: DrawerState,
    state: AppDrawerContract.UiState,
    onDrawerDestinationClick: (DrawerScreenDestination) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        modifier = modifier,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onDrawerDestinationClick(DrawerScreenDestination.Profile) }
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    val initials = state.activeAccount.fullName()
                        .split(" ")
                        .take(2)
                        .joinToString("") { it.first().uppercase() }

                    InitialsAvatar(
                        initials = initials,
                        modifier = Modifier.size(64.dp),
                    )

                    Text(
                        text = state.activeAccount.fullName(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                    Text(
                        text = state.activeAccount.email,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
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
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        menuItems.forEach {
            NavigationDrawerItem(
                label = { Text(text = it.label()) },
                selected = false,
                onClick = { onDrawerDestinationClick(it) },
            )
        }
    }
}

sealed class DrawerScreenDestination {
    data object Home : DrawerScreenDestination()
    data object Profile : DrawerScreenDestination()
    data class SignOut(val userId: String) : DrawerScreenDestination()
}

private fun DrawerScreenDestination.label(): String =
    when (this) {
        DrawerScreenDestination.Home -> "Home"
        DrawerScreenDestination.Profile -> "My Profile"
        is DrawerScreenDestination.SignOut -> "Sign out"
    }
