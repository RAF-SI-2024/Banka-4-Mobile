package rs.raf.rafeisen.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import rs.raf.rafeisen.screen.home.HomeScreen
import rs.raf.rafeisen.screen.home.HomeViewModel
import rs.raf.rafeisen.screen.login.LoginScreen
import rs.raf.rafeisen.screen.login.LoginViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home",
    ) {
        home(route = "home", navController = navController)
        login(route = "login", navController = navController)
    }
}

private fun NavGraphBuilder.login(
    route: String,
    navController: NavController,
) = composable(
    route = route,
) {
    val viewModel = hiltViewModel<LoginViewModel>()
    LoginScreen(viewModel = viewModel)
}

private fun NavGraphBuilder.home(
    route: String,
    navController: NavController,
) = composable(
    route = route,
) {
    val viewModel = hiltViewModel<HomeViewModel>()
    HomeScreen(viewModel = viewModel)
}
