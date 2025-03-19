package rs.raf.rafeisen

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.AndroidEntryPoint
import rs.raf.rafeisen.navigation.AppNavigation
import rs.raf.rafeisen.ui.theme.RAFeisenTheme

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RAFeisenTheme {
                AppNavigation()
            }
        }
    }
}
