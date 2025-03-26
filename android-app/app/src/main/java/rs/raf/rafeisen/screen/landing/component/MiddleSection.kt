package rs.raf.rafeisen.screen.landing.component


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.StarHalf
import androidx.compose.material.icons.rounded.MonetizationOn
import androidx.compose.ui.tooling.preview.Preview
import rs.raf.rafeisen.ui.theme.OrangeStart
import rs.raf.rafeisen.ui.theme.GreenStart

data class Finance(
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val name: String,
    val background: Color
)


val financeList = listOf(
    Finance(
        icon = Icons.Rounded.StarHalf,
        name = "My\nContacts",
        background = OrangeStart
    ),

    Finance(
        icon = Icons.Rounded.MonetizationOn,
        name = "My\nTransactions",
        background = GreenStart
    )
)

@Composable
fun FinanceSection() {
    Column {
        Text(
            text = "Dashboard",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(16.dp)
        )
        LazyRow {
            items(financeList.size) { index ->
                FinanceItem(
                    finance = financeList[index],
                    isLastItem = index == financeList.lastIndex
                )
            }
        }
    }
}

@Composable
fun FinanceItem(finance: Finance, isLastItem: Boolean) {
    val endPadding = if (isLastItem) 16.dp else 0.dp
    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = endPadding)
            .clip(RoundedCornerShape(25.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(13.dp)
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(finance.background)
                .padding(6.dp)
        ) {
            Icon(
                imageVector = finance.icon,
                contentDescription = finance.name,
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = finance.name,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            fontSize = 15.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFinanceSection() {
    FinanceSection()
}
