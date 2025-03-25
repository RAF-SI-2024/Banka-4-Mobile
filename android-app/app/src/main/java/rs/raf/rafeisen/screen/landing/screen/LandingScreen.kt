package rs.raf.rafeisen.screen.landing

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import rs.raf.rafeisen.screen.landing.mock.mockCards

@Composable
fun LandingScreen(
    viewModel: LandingViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit = {}
) {
    val state = viewModel.state.collectAsState().value
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
            if (state.isLoading) {
                CircularProgressIndicator()
            } else if (state.error != null) {
                Text(text = "Error: ${state.error}")
            } else {
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


