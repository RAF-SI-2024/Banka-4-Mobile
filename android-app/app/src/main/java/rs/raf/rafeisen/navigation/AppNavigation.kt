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
import rs.raf.rafeisen.screen.totp.AddTotpScreen
import rs.raf.rafeisen.screen.totp.AddTotpViewModel

@Composable
fun AppNavigation(startDestination: String) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        home(route = "home", navController = navController)
        login(route = "login", navController = navController)
        addTotp(route = "totp", navController = navController)
    }
}

private fun NavGraphBuilder.login(
    route: String,
    navController: NavController,
) = composable(
    route = route,
) {
    val viewModel = hiltViewModel<LoginViewModel>()
    LoginScreen(
        viewModel = viewModel,
        onNavigateToHome = { navController.navigateToHome() },
    )
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

private fun NavGraphBuilder.addTotp(
    route: String,
    navController: NavController,
) = composable(route = route) {
    val viewModel = hiltViewModel<AddTotpViewModel>()

    AddTotpScreen(
        viewModel = viewModel,
        onClose = {
            navController.popBackStack()
            navController.navigateUp()
        },
    )
}
