package rs.raf.rafeisen.screen.landing

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import rs.raf.rafeisen.screen.home.mock.HomeMockData
import rs.raf.rafeisen.screen.home.mock.TransactionMock
import java.text.DecimalFormat

@Composable
fun TransactionsSection(transactions: List<TransactionMock>) {
    var isVisible by remember { mutableStateOf(false) }
    var iconState by remember { mutableStateOf(Icons.Rounded.KeyboardArrowDown) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .animateContentSize()
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .background(MaterialTheme.colorScheme.inverseOnSurface)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondary)
                    .clickable {
                        isVisible = !isVisible
                        iconState = if (isVisible) Icons.Rounded.KeyboardArrowUp else Icons.Rounded.KeyboardArrowDown
                    }
            ) {
                Icon(
                    imageVector = iconState,
                    contentDescription = "Transactions",
                    tint = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.size(25.dp)
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "Transactions",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondaryContainer)
        )
        if (isVisible) {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                    .background(MaterialTheme.colorScheme.background)
            ) {
                val totalWidth = maxWidth
                val dateWidth = totalWidth * 0.25f
                val descriptionWidth = totalWidth * 0.5f
                val amountWidth = totalWidth * 0.25f

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            modifier = Modifier.width(dateWidth),
                            text = "Date",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            modifier = Modifier.width(descriptionWidth),
                            text = "Description",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            modifier = Modifier.width(amountWidth),
                            text = "Amount",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.End
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    LazyColumn {
                        itemsIndexed(transactions) { _, transaction ->
                            TransactionTableItem(
                                transaction = transaction,
                                dateWidth = dateWidth,
                                descriptionWidth = descriptionWidth,
                                amountWidth = amountWidth
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TransactionTableItem(
    transaction: TransactionMock,
    dateWidth: Dp,
    descriptionWidth: Dp,
    amountWidth: Dp
) {
    val amountColor = if (transaction.amount < 0) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            modifier = Modifier.width(dateWidth),
            text = transaction.date,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            modifier = Modifier.width(descriptionWidth),
            text = transaction.description,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            modifier = Modifier.width(amountWidth),
            text = formatTransactionAmount(transaction.amount, transaction.currency),
            fontSize = 14.sp,
            color = amountColor,
            textAlign = TextAlign.End
        )
    }
}

fun formatTransactionAmount(amount: Double, currency: String): String {
    val df = DecimalFormat("#,###.00")
    return "${df.format(amount)} $currency"
}

@Preview(showBackground = true)
@Composable
fun PreviewTransactionsSection() {
    TransactionsSection(transactions = HomeMockData.getMockTransactions())
}
