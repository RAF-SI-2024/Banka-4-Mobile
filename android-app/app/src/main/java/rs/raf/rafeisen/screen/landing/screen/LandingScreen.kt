package rs.raf.rafeisen.screen.landing

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import rs.raf.rafeisen.screen.landing.mock.mockCards

@Composable
fun LandingScreen(
    viewModel: LandingViewModel,
    onNavigateToHome: () -> Unit
) {
    val state = viewModel.state.collectAsState().value
    LandingScreen(
        state = state,
        onNavigateToHome = onNavigateToHome
    )
}

@Composable
private fun LandingScreen(
    state: LandingContract.LandingUIState,
    onNavigateToHome: () -> Unit
) {
    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedIndex = selectedIndex,
                onItemSelected = { index ->
                    selectedIndex = index
                    if (index == 2) {
                        onNavigateToHome()
                    }
                }
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                state.isLoading -> CircularProgressIndicator()
                state.error != null -> Text(text = "Error: ${state.error}")
                else -> {
                    Column(modifier = Modifier.padding(paddingValues)) {
                        Spacer(modifier = Modifier.height(16.dp))
                        CardSection(cards = mockCards)
                        state.account?.let { account ->
                            AccountBalanceSection(
                                accountName = account.accountName,
                                accountNumber = account.accountNumber,
                                availableBalance = account.availableBalance,
                                currency = account.currency
                            )
                        }
                        FinanceSection()
                        TransactionsSection(transactions = state.transactions)
                    }
                }
            }
        }
    }
}
