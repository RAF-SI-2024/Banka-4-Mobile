package rs.raf.rafeisen.screen.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.StarHalf
import androidx.compose.material.icons.rounded.MonetizationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import rs.raf.rafeisen.screen.home.model.QuickAccessUi
import rs.raf.rafeisen.ui.theme.GreenStart
import rs.raf.rafeisen.ui.theme.OrangeStart

@Composable
fun QuickAccessColumn(modifier: Modifier = Modifier) {
    val quickAccessUiLists = makeQuickAccessUiList()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = "Quick Access",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(
                items = quickAccessUiLists,
                key = { it.name },
            ) { quickAccess ->
                QuickAccessItem(quickAccessUi = quickAccess)
            }
        }
    }
}

@Composable
fun QuickAccessItem(
    quickAccessUi: QuickAccessUi,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .background(quickAccessUi.background)
                .padding(4.dp),
        ) {
            Icon(
                imageVector = quickAccessUi.icon,
                contentDescription = quickAccessUi.name,
                tint = Color.White,
            )
        }
        Text(
            text = quickAccessUi.name,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
        )
    }
}

private fun makeQuickAccessUiList() =
    listOf(
        QuickAccessUi(
            icon = Icons.AutoMirrored.Rounded.StarHalf,
            name = "My Contacts",
            background = OrangeStart,
        ),

        QuickAccessUi(
            icon = Icons.Rounded.MonetizationOn,
            name = "My Transactions",
            background = GreenStart,
        ),
    )

@Preview(showBackground = true)
@Composable
private fun PreviewQuickAccessSection() {
    QuickAccessColumn()
}
