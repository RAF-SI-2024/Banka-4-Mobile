package rs.raf.rafeisen.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import rs.raf.rafeisen.drawer.AppDrawerScaffold
import rs.raf.rafeisen.drawer.DrawerScreenDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onDrawerScreenDestinationClick: (DrawerScreenDestination) -> Unit,
    onAddTotpClick: () -> Unit,
) {
    HomeScreen(
        onDrawerScreenDestinationClick = onDrawerScreenDestinationClick,
        onAddTotpClick = onAddTotpClick,
    )
}

@ExperimentalMaterial3Api
@Composable
private fun HomeScreen(
    onDrawerScreenDestinationClick: (DrawerScreenDestination) -> Unit,
    onAddTotpClick: () -> Unit,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    AppDrawerScaffold(
        drawerState = drawerState,
        onDrawerScreenDestinationClick = onDrawerScreenDestinationClick,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTotpClick,
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        topBar = { HomeTopAppBar(drawerState = drawerState) },
        content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues),
            ) {
                Text(text = "home")
            }
        }
    )
}

@ExperimentalMaterial3Api

@Composable
private fun HomeTopAppBar(
    drawerState: DrawerState
) {
    val uiScope = rememberCoroutineScope()

    CenterAlignedTopAppBar(
        title = { Text(text = "Home") },
        navigationIcon = {
            IconButton(
                onClick = {
                    uiScope.launch {
                        if (drawerState.isOpen) {
                            drawerState.close()
                        } else {
                            drawerState.open()
                        }
                    }
                },
            ) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = null)
            }
        }
    )
}
