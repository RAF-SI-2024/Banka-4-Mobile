package rs.raf.rafeisen.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import rs.raf.rafeisen.utills.*
import rs.raf.rafeisen.screen.home.mock.TransactionMock
import rs.raf.rafeisen.utills.AccountInfoCard
import rs.raf.rafeisen.utills.TransactionItem
import rs.raf.rafeisen.utills.UserInfoCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value

    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Home") })
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (state.error != null) {
                    item {
                        Text(
                            text = "Error: ${state.error}",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }

                item {
                    UserInfoCard(
                        clientName = state.clientName,
                        email = state.email,
                        phone = state.phone,
                        address = state.address
                    )
                }

                state.account?.let { account ->
                    item {
                        AccountInfoCard(
                            accountName = account.accountName,
                            accountNumber = account.accountNumber,
                            availableBalance = account.availableBalance,
                            currency = account.currency,
                            incomingPayments = account.incomingPayments,
                            outgoingPayments = account.outgoingPayments,
                            allowedOverdraft = account.allowedOverdraft
                        )
                    }

                    item {
                        Text(
                            text = "Recent Transactions",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    items(state.transactions) { tx: TransactionMock ->
                        TransactionItem(transaction = tx)
                    }
                }
            }
        }
    }
}
