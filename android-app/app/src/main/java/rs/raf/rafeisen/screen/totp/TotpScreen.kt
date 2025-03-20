package rs.raf.rafeisen.screen.totp

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TotpScreen(
    viewModel: TotpViewModel = hiltViewModel(),
    onBack: () -> Unit = {}
) {
    val state = viewModel.state.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add TOTP Code") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            OutlinedTextField(
                value = state.issuer,
                onValueChange = { viewModel.onEvent(TotpContract.TotpUiEvent.OnIssuerChanged(it)) },
                label = { Text("Issuer") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = state.secret,
                onValueChange = { viewModel.onEvent(TotpContract.TotpUiEvent.OnSecretChanged(it)) },
                label = { Text("Secret") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { viewModel.onEvent(TotpContract.TotpUiEvent.Submit) },
                enabled = !state.isSubmitting,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (state.isSubmitting) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                } else {
                    Text("Add TOTP Code")
                }
            }
            if (state.errorMessage != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = state.errorMessage, color = MaterialTheme.colorScheme.error)
            }
            if (state.success) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "TOTP code added successfully!", color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}
