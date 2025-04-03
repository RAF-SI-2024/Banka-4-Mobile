package rs.raf.rafeisen.screen.logout

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState

@Composable
fun LogoutScreen(
    viewModel: LogoutViewModel,
    onClose: () -> Unit,
    navigateToLogin: () -> Unit,
) {
    val currentOnClose by rememberUpdatedState(onClose)
    val currentNavigateToLogin by rememberUpdatedState(navigateToLogin)

    LaunchedEffect(viewModel, viewModel.effects) {
        viewModel.effects.collect {
            when (it) {
                LogoutContract.SideEffect.LogoutSuccessful -> {
                    currentOnClose()
                    currentNavigateToLogin()
                }
            }
        }
    }

    LogoutScreen(
        onClose = onClose,
        onLogoutRequest = { viewModel.setEvent(LogoutContract.UiEvent.LogoutConfirmed) },
    )
}

@Composable
private fun LogoutScreen(onLogoutRequest: () -> Unit, onClose: () -> Unit) {
    AlertDialog(
        onDismissRequest = onClose,
        title = @Composable {
            Text(text = "Sign Out")
        },
        text = @Composable {
            Text(text = "Are you sure you want to sign out?")
        },
        dismissButton = @Composable {
            TextButton(onClick = onClose) {
                Text(text = "Cancel")
            }
        },
        confirmButton = @Composable {
            TextButton(onClick = onLogoutRequest) {
                Text(text = "Sign out")
            }
        },
    )
}
