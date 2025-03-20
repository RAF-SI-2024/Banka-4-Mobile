package rs.raf.rafeisen.utills

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import rs.raf.rafeisen.screen.home.mock.TransactionMock
import java.text.DecimalFormat

@Composable
fun UserInfoCard(
    clientName: String,
    email: String,
    phone: String,
    address: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Avatar",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(48.dp)
                    .alpha(0.8f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = clientName,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Email: $email",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Phone: $phone",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Address: $address",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun AccountInfoCard(
    accountName: String,
    accountNumber: String,
    availableBalance: Double,
    currency: String,
    incomingPayments: Double,
    outgoingPayments: Double,
    allowedOverdraft: Double
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Available Balance",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = formatAmount(availableBalance, currency),
                style = MaterialTheme.typography.displaySmall
            )
            Divider()
            Text(
                text = accountName,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = accountNumber,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Incoming Payments: ${formatAmount(incomingPayments, currency)}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Outgoing Payments: ${formatAmount(outgoingPayments, currency)}",
                style = MaterialTheme.typography.bodyMedium
            )
            Divider()
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Balance", style = MaterialTheme.typography.labelLarge)
                    Text(
                        text = formatAmount(availableBalance, currency),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Column {
                    Text(text = "Allowed Overdraft", style = MaterialTheme.typography.labelLarge)
                    Text(
                        text = formatAmount(allowedOverdraft, currency),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}

@Composable
fun TransactionItem(transaction: TransactionMock) {
    val df = DecimalFormat("#,###.00")
    val amountText = "${df.format(transaction.amount)} ${transaction.currency}"
    val amountColor = if (transaction.amount < 0) {
        MaterialTheme.colorScheme.error
    } else {
        Color(0xFF2E7D32)
    }
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = transaction.date, style = MaterialTheme.typography.bodySmall)
                Text(text = transaction.description, style = MaterialTheme.typography.bodyLarge)
            }
            Text(
                text = amountText,
                style = MaterialTheme.typography.bodyLarge,
                color = amountColor
            )
        }
    }
}

fun formatAmount(amount: Double, currency: String): String {
    val df = DecimalFormat("#,###.00")
    return "${df.format(amount)} $currency"
}
