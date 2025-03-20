package rs.raf.rafeisen

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.AndroidEntryPoint
import rs.raf.rafeisen.navigation.AppNavigation
import rs.raf.rafeisen.splash.SplashViewModel
import rs.raf.rafeisen.ui.theme.RAFeisenTheme

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().apply {
            setKeepOnScreenCondition { !splashViewModel.isAuthCheckComplete.value }
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RAFeisenTheme {
                val isLoggedIn = splashViewModel.isLoggedIn.collectAsState()

                AppNavigation(startDestination = if (isLoggedIn.value) "home" else "login")
            }
        }
    }
}
