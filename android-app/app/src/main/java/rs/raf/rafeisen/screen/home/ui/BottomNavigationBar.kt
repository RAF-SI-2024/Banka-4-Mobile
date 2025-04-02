package rs.raf.rafeisen.screen.home.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun BottomNavigationBar(
    selectedItem: BottomNavigationDestination,
    onDestinationChanged: (BottomNavigationDestination) -> Unit,
) {
    val bottomNavItems = listOf(
        BottomNavigationDestination.Home,
        BottomNavigationDestination.Totp,
        BottomNavigationDestination.Profile,
    )
    NavigationBar {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                selected = item == selectedItem,
                onClick = { onDestinationChanged(item) },
                icon = {
                    Icon(
                        imageVector = item.toIcon(),
                        tint = MaterialTheme.colorScheme.onBackground,
                        contentDescription = null,
                    )
                },
                label = {
                    Text(
                        text = item.toLabel(),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 10.sp,
                    )
                },
            )
        }
    }
}

fun BottomNavigationDestination.toIcon() =
    when (this) {
        BottomNavigationDestination.Home -> Icons.Default.Home
        BottomNavigationDestination.Totp -> Icons.Default.Key
        BottomNavigationDestination.Profile -> Icons.Default.Person
    }

fun BottomNavigationDestination.toLabel() =
    when (this) {
        BottomNavigationDestination.Home -> "Home"
        BottomNavigationDestination.Profile -> "Profile"
        BottomNavigationDestination.Totp -> "TOTP"
    }

sealed class BottomNavigationDestination {
    data object Home : BottomNavigationDestination()
    data object Totp : BottomNavigationDestination()
    data object Profile : BottomNavigationDestination()
}

@Preview(showBackground = true)
@Composable
private fun PreviewBottomNavigationBar() {
    BottomNavigationBar(
        selectedItem = BottomNavigationDestination.Home,
        onDestinationChanged = {},
    )
}
