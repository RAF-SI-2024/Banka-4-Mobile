package rs.raf.rafeisen.screen.landing.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import rs.raf.rafeisen.drawer.AppDrawerScaffold
import rs.raf.rafeisen.drawer.DrawerScreenDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingDrawerScaffold(
    modifier: Modifier = Modifier,
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    onDrawerDestinationClick: (DrawerScreenDestination) -> Unit,
    onBottomItemClick: (Int) -> Unit = {},
    topBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    var selectedIndex by remember { mutableStateOf(0) }

    AppDrawerScaffold(
        drawerState = drawerState,
        modifier = modifier,
        onDrawerScreenDestinationClick = onDrawerDestinationClick,
        topBar = topBar,
        bottomBar = {
            BottomNavigationBar(
                selectedIndex = selectedIndex,
                onItemSelected = { index ->
                    selectedIndex = index
                    onBottomItemClick(index)
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        content = content
    )
}
