package rs.raf.rafeisen.screen.landing.component

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.StarHalf
import androidx.compose.material.icons.rounded.MonetizationOn
import androidx.compose.ui.tooling.preview.Preview
import rs.raf.rafeisen.ui.theme.OrangeStart
import rs.raf.rafeisen.ui.theme.GreenStart

data class QuickAccess(
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val name: String,
    val background: Color
)


val quickAccessList = listOf(
    QuickAccess(
        icon = Icons.Rounded.StarHalf,
        name = "My Contacts",
        background = OrangeStart
    ),

    QuickAccess(
        icon = Icons.Rounded.MonetizationOn,
        name = "My Transactions",
        background = GreenStart
    )
)

@Composable
fun QuickAccessSection() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Quick Access",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                items = quickAccessList,
                key = { it.name }
            ) { quickAccess ->
                QuickAccessItem(quickAccess = quickAccess)
            }
        }
    }
}

@Composable
fun QuickAccessItem(quickAccess: QuickAccess) {
    Column(
        modifier = Modifier
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .background(quickAccess.background)
                .padding(4.dp)
        ) {
            Icon(
                imageVector = quickAccess.icon,
                contentDescription = quickAccess.name,
                tint = Color.White
            )
        }
        Text(
            text = quickAccess.name,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewQuickAccessSection() {
    QuickAccessSection()
}
