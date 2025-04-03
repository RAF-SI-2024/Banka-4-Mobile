package rs.raf.rafeisen.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import rs.raf.rafeisen.core.ui.ErrorScreen
import rs.raf.rafeisen.core.ui.LoadingSpinner
import rs.raf.rafeisen.core.ui.NavigationScaffold
import rs.raf.rafeisen.drawer.DrawerScreenDestination
import rs.raf.rafeisen.screen.home.ui.AccountBalanceSection
import rs.raf.rafeisen.screen.home.ui.BottomNavigationDestination
import rs.raf.rafeisen.screen.home.ui.CardSection
import rs.raf.rafeisen.screen.home.ui.QuickAccessColumn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onDrawerScreenDestinationClick: (DrawerScreenDestination) -> Unit,
    onBottomBarDestinationClick: (BottomNavigationDestination) -> Unit,
) {
    val state = viewModel.state.collectAsState()

    HomeScreen(
        state = state.value,
        eventPublisher = viewModel::setEvent,
        onDrawerScreenDestinationClick = onDrawerScreenDestinationClick,
        onBottomBarDestinationClick = onBottomBarDestinationClick,
    )
}

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(
    state: HomeContract.UiState,
    eventPublisher: (HomeContract.UiEvent) -> Unit,
    onDrawerScreenDestinationClick: (DrawerScreenDestination) -> Unit,
    onBottomBarDestinationClick: (BottomNavigationDestination) -> Unit,
    modifier: Modifier = Modifier,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    NavigationScaffold(
        drawerState = drawerState,
        onDrawerDestinationClick = onDrawerScreenDestinationClick,
        onBottomBarDestinationClick = onBottomBarDestinationClick,
        topBarTitleText = "Overview",
        selectedBottomBarDestination = BottomNavigationDestination.Home,
    ) { paddingValues ->
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
        ) {
            when {
                state.isLoading -> LoadingSpinner(modifier = Modifier.fillMaxSize())

                state.error != null -> ErrorScreen(
                    message = "Something went wrong. Please try again.",
                    onRetry = { eventPublisher(HomeContract.UiEvent.LoadAccountAndCardsData) },
                )
                /* TODO: handle no accounts state. */
                else -> {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        if (state.accounts.isNotEmpty()) {
                            AccountBalanceSection(accounts = state.accounts)
                        }
                        CardSection(cards = state.cards)
                        QuickAccessColumn()
                    }
                }
            }
        }
    }
}
