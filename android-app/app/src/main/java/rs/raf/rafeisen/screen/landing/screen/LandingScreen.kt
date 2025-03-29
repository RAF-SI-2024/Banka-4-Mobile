package rs.raf.rafeisen.screen.landing.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import rs.raf.rafeisen.drawer.DrawerScreenDestination
import rs.raf.rafeisen.screen.landing.component.*
import rs.raf.rafeisen.utils.ErrorScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingScreen(
    viewModel: LandingViewModel,
    onDrawerScreenDestinationClick: (DrawerScreenDestination) -> Unit,
    onNavigateToTotp: () -> Unit,
) {
    val state = viewModel.state.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    LandingDrawerScaffold(
        drawerState = drawerState,
        onDrawerDestinationClick = onDrawerScreenDestinationClick,
        onBottomItemClick = { index ->
            if (index == 2) onNavigateToTotp()
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Overview") },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            if (drawerState.isOpen) drawerState.close()
                            else drawerState.open()
                        }
                    }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                }
            )
        }
    ) { paddingValues ->
        LandingScreenContent(
            state = state.value,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            onReload = {
                viewModel.sendEvent(LandingContract.LandingUIEvent.LoadAccountAndCardsData)
            }
        )
    }
}


@Composable
private fun LandingScreenContent(
    state: LandingContract.LandingUIState,
    modifier: Modifier = Modifier,
    onReload: () -> Unit
) {
    Surface(modifier = modifier) {
        when {
            state.isLoading -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(strokeWidth = 2.dp)
            }

            state.error != null -> ErrorScreen(
                message = state.error,
                onRetry = onReload
            )


            else -> {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (state.accounts.isNotEmpty()) {
                        AccountBalanceSection(accounts = state.accounts)
                    }
                    CardSection(cards = state.cards)

                    QuickAccessSection()
                }
            }
        }
    }
}

