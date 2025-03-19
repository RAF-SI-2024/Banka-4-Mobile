package rs.raf.rafeisen.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    HomeScreen()
}

@Composable
private fun HomeScreen() {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
        ) {
            Text(text = "home")
        }
    }
}
