package rs.raf.rafeisen.totp.home

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import java.time.Instant
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import rs.raf.rafeisen.core.ui.NavigationScaffold
import rs.raf.rafeisen.drawer.DrawerScreenDestination
import rs.raf.rafeisen.screen.home.ui.BottomNavigationDestination
import rs.raf.rafeisen.totp.model.TotpUiModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TotpScreen(
    viewModel: TotpViewModel,
    onDrawerScreenDestinationClick: (DrawerScreenDestination) -> Unit,
    onBottomBarDestinationClick: (BottomNavigationDestination) -> Unit,
    onAddTotpClick: () -> Unit,
) {
    val uiState = viewModel.state.collectAsState()

    TotpScreen(
        state = uiState.value,
        onAddTotpClick = onAddTotpClick,
        onDrawerScreenDestinationClick = onDrawerScreenDestinationClick,
        onBottomBarDestinationClick = onBottomBarDestinationClick,
    )
}

@ExperimentalMaterial3Api
@Composable
private fun TotpScreen(
    state: TotpContract.UiState,
    onDrawerScreenDestinationClick: (DrawerScreenDestination) -> Unit,
    onBottomBarDestinationClick: (BottomNavigationDestination) -> Unit,
    onAddTotpClick: () -> Unit,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var currentProgress by remember { mutableFloatStateOf(1f) }

    LaunchedEffect(Unit) {
        launch {
            while (true) {
                delay(1.seconds)
                val timestamp = Instant.now().epochSecond
                currentProgress = 1f - ((timestamp % 30) / 29f)
            }
        }
    }

    NavigationScaffold(
        drawerState = drawerState,
        topBarTitleText = "TOTP",
        selectedBottomBarDestination = BottomNavigationDestination.Totp,
        onBottomBarDestinationClick = onBottomBarDestinationClick,
        onDrawerDestinationClick = onDrawerScreenDestinationClick,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTotpClick,
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
            ) {
                if (state.totpCodes.isEmpty() && state.isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                } else if (state.totpCodes.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(text = "You don't have any codes yet.")
                    }
                } else {
                    state.totpCodes.forEach { (_, totp) ->
                        CodeCard(
                            progress = currentProgress,
                            modifier = Modifier.padding(horizontal = 16.dp),
                            totp = totp,
                        )
                    }
                }
            }
        },
    )
}

@Composable
private fun CodeCard(
    modifier: Modifier = Modifier,
    totp: TotpUiModel,
    progress: Float,
) {
    val clipboardManager = LocalClipboardManager.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(12.dp),
            )
            .clickable {
                clipboardManager.setText(
                    annotatedString = buildAnnotatedString { append(totp.code) },
                )
            }
            .padding(vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = totp.issuer,
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            text = totp.code,
            style = MaterialTheme.typography.displayMedium,
        )
        LinearProgressIndicator(
            progress = { progress },
        )
    }
}

@ExperimentalMaterial3Api
@Composable
private fun HomeTopAppBar(drawerState: DrawerState) {
    val uiScope = rememberCoroutineScope()

    CenterAlignedTopAppBar(
        title = { Text(text = "Home") },
        navigationIcon = {
            IconButton(
                onClick = {
                    uiScope.launch {
                        if (drawerState.isOpen) {
                            drawerState.close()
                        } else {
                            drawerState.open()
                        }
                    }
                },
            ) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = null)
            }
        },
    )
}
