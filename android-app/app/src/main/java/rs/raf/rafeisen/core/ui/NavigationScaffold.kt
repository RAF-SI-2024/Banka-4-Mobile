package rs.raf.rafeisen.core.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.launch
import rs.raf.rafeisen.drawer.AppDrawerScaffold
import rs.raf.rafeisen.drawer.DrawerScreenDestination
import rs.raf.rafeisen.screen.home.ui.BottomNavigationBar
import rs.raf.rafeisen.screen.home.ui.BottomNavigationDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationScaffold(
    modifier: Modifier = Modifier,
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    topBarTitleText: String,
    selectedBottomBarDestination: BottomNavigationDestination,
    onDrawerDestinationClick: (DrawerScreenDestination) -> Unit,
    onBottomBarDestinationClick: (BottomNavigationDestination) -> Unit,
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(containerColor),
    content: @Composable (PaddingValues) -> Unit,
) {
    val scope = rememberCoroutineScope()

    AppDrawerScaffold(
        drawerState = drawerState,
        modifier = modifier,
        onDrawerScreenDestinationClick = onDrawerDestinationClick,
        snackbarHost = snackbarHost,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        contentColor = contentColor,
        containerColor = containerColor,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = topBarTitleText) },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                if (drawerState.isOpen) {
                                    drawerState.close()
                                } else {
                                    drawerState.open()
                                }
                            }
                        },
                    ) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
            )
        },
        bottomBar = {
            BottomNavigationBar(
                selectedItem = selectedBottomBarDestination,
                onDestinationChanged = onBottomBarDestinationClick,
            )
        },
        content = content,
    )
}
