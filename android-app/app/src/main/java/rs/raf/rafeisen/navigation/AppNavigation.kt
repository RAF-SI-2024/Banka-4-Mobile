package rs.raf.rafeisen.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavArgument
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import rs.raf.rafeisen.drawer.DrawerScreenDestination
import rs.raf.rafeisen.screen.home.HomeScreen
import rs.raf.rafeisen.screen.home.HomeViewModel
import rs.raf.rafeisen.screen.login.LoginScreen
import rs.raf.rafeisen.screen.login.LoginViewModel
import rs.raf.rafeisen.screen.logout.LogoutScreen
import rs.raf.rafeisen.screen.logout.LogoutViewModel
import rs.raf.rafeisen.totp.add.AddTotpScreen
import rs.raf.rafeisen.totp.add.AddTotpViewModel

@Composable
fun AppNavigation(startDestination: String) {
    val navController = rememberNavController()

    val drawerDestinationHandler: (DrawerScreenDestination) -> Unit = {
        when (it) {
            DrawerScreenDestination.Home -> navController.navigateToHome()
            /* TODO: use userId to logout given user, this will be used later for multi-account support */
            is DrawerScreenDestination.SignOut -> navController.navigateToLogout()
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        home(
            route = "home",
            navController = navController,
            onDrawerScreenDestination = drawerDestinationHandler,
        )
        login(route = "login", navController = navController)
        logout(route = "logout", navController = navController)
        addTotp(
            route = "totp",
            arguments = listOf(
                navArgument(SECRET) {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument(ISSUER) {
                    type = NavType.StringType
                    nullable = true
                }
            ),
            navController = navController,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "otpauth://totp/.*?secret={$SECRET}&issuer={$ISSUER}&.*"
                }
            )
        )
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

private fun NavGraphBuilder.logout(
    route: String,
    navController: NavController,
) = dialog(
    route = route,
) {
    val viewModel = hiltViewModel<LogoutViewModel>()

    LogoutScreen(
        viewModel = viewModel,
        onClose = { navController.popBackStack() },
        navigateToLogin = { navController.navigateToLogin() },
    )
}

private fun NavGraphBuilder.home(
    route: String,
    navController: NavController,
    onDrawerScreenDestination: (DrawerScreenDestination) -> Unit,
) = composable(
    route = route,
) {
    val viewModel = hiltViewModel<HomeViewModel>()
    HomeScreen(
        viewModel = viewModel,
        onDrawerScreenDestinationClick = onDrawerScreenDestination,
        onAddTotpClick = { navController.navigateToAddTotp() },
    )
}

private fun NavGraphBuilder.addTotp(
    route: String,
    arguments: List<NamedNavArgument>,
    deepLinks: List<NavDeepLink>,
    navController: NavController,
) = composable(
    route = route,
    arguments = arguments,
    deepLinks = deepLinks,
) {
    val viewModel = hiltViewModel<AddTotpViewModel>()

    AddTotpScreen(
        viewModel = viewModel,
        navigateToHome = {
            navController.popBackStack()
            navController.navigateToHome()
        },
        onClose = { navController.popBackStack() },
    )
}
