package rs.raf.rafeisen.totp.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun AddTotpScreen(
    viewModel: AddTotpViewModel,
    onClose: () -> Unit,
) {
    val uiState = viewModel.state.collectAsState()

    LaunchedEffect(viewModel) {
        launch {
            viewModel.effects.collect {
                when (it) {
                    AddTotpContract.SideEffect.TotpCodeAddedSuccessfully -> onClose()
                }
            }
        }
    }

    AddTotpScreen(
        state = uiState.value,
        eventPublisher = viewModel::setEvent,
        onClose = onClose,
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTotpScreen(
    state: AddTotpContract.UiState,
    eventPublisher: (AddTotpContract.UiEvent) -> Unit,
    onClose: () -> Unit,
) {
    var issuer by remember { mutableStateOf("") }
    var secret by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier
            .imePadding(),
        topBar = {
            TopAppBar(
                title = { Text("Add TOTP Code") },
                navigationIcon = {
                    IconButton(onClick = onClose) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                        )
                    }
                }
            )
        },
        bottomBar = {
            AddTotpBottomBar(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 24.dp)
                    .navigationBarsPadding(),
                onAddTotpClick = {
                    eventPublisher(
                        AddTotpContract.UiEvent.AddTotpCode(
                            issuer = issuer,
                            secret = secret,
                        )
                    )
                },
                disabled = state.isWorking || issuer.isBlank() || secret.isBlank(),
                working = state.isWorking,
            )
        }
    ) { paddingValues ->
        AddTotpForm(
            modifier = Modifier.padding(paddingValues),
            issuer = issuer,
            onIssuerChanged = { issuer = it },
            secret = secret,
            onSecretChanged = { secret = it },
        )
    }
}

@Composable
fun AddTotpBottomBar(
    modifier: Modifier = Modifier,
    onAddTotpClick: () -> Unit,
    disabled: Boolean,
    working: Boolean,
) {
    Button(
        onClick = onAddTotpClick,
        enabled = !disabled && !working,
        modifier = modifier
            .height(56.dp)
            .fillMaxWidth(),
    ) {
        if (working) {
            CircularProgressIndicator(modifier = Modifier.size(24.dp))
        } else {
            Text("Add TOTP Code")
        }
    }
}

@Composable
private fun AddTotpForm(
    modifier: Modifier = Modifier,
    issuer: String,
    secret: String,
    onIssuerChanged: (String) -> Unit,
    onSecretChanged: (String) -> Unit,
) {

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        OutlinedTextField(
            value = issuer,
            onValueChange = onIssuerChanged,
            label = { Text("Issuer") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = secret,
            onValueChange = onSecretChanged,
            label = { Text("Secret") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}
