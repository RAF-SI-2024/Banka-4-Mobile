package rs.raf.rafeisen.screen.landing.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rs.raf.rafeisen.screen.landing.mock.AccountMock
import rs.raf.rafeisen.utils.formatAmount

@Composable
fun AccountBalanceSection(accounts: List<AccountMock>) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "Account Information",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                items = accounts,
                key = { it.id }
            ) { account ->
                AccountBalanceCard(account)
            }
        }
    }
}

@Composable
fun AccountBalanceCard(account: AccountMock) {
    Card(
        modifier = Modifier
            .width(280.dp)
            .height(IntrinsicSize.Min),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "${account.client.firstName} ${account.client.lastName}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = account.accountNumber,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Type: ${account.accountType}",
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = "Opened: ${account.createdDate}",
                    style = MaterialTheme.typography.labelSmall
                )
                Text(
                    text = "Expires: ${account.expirationDate}",
                    style = MaterialTheme.typography.labelSmall
                )
            }

            HorizontalDivider()

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Available Balance",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = formatAmount(account.availableBalance, account.currency.code),
                    style = MaterialTheme.typography.displaySmall,
                    fontSize = 24.sp
                )
            }
        }
    }
}
