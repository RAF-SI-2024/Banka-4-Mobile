package rs.raf.rafeisen.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
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
import rs.raf.rafeisen.screen.home.ui.BottomNavigationDestination
import rs.raf.rafeisen.screen.login.LoginScreen
import rs.raf.rafeisen.screen.login.LoginViewModel
import rs.raf.rafeisen.screen.logout.LogoutScreen
import rs.raf.rafeisen.screen.logout.LogoutViewModel
import rs.raf.rafeisen.totp.add.AddTotpScreen
import rs.raf.rafeisen.totp.add.AddTotpViewModel
import rs.raf.rafeisen.totp.home.TotpScreen
import rs.raf.rafeisen.totp.home.TotpViewModel

@Composable
fun AppNavigation(startDestination: String) {
    val navController = rememberNavController()

    val drawerDestinationHandler: (DrawerScreenDestination) -> Unit = {
        when (it) {
            DrawerScreenDestination.Home -> navController.navigateToHome()
            is DrawerScreenDestination.SignOut -> navController.navigateToLogout()
        }
    }

    val bottomBarDestinationHandler: (BottomNavigationDestination) -> Unit = {
        when (it) {
            BottomNavigationDestination.Home -> navController.navigateToHome()
            BottomNavigationDestination.Profile -> {}
            BottomNavigationDestination.Totp -> navController.navigateToTotp()
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
            onBottomBarDestination = bottomBarDestinationHandler,
        )
        totp(
            route = "totp",
            navController = navController,
            onDrawerScreenDestination = drawerDestinationHandler,
            onBottomBarDestination = bottomBarDestinationHandler,
        )
        login(
            route = "login",
            navController = navController,
        )
        logout(
            route = "logout",
            navController = navController,
        )
        addTotp(
            route = "addTotp",
            arguments = listOf(
                navArgument(SECRET) {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument(ISSUER) {
                    type = NavType.StringType
                    nullable = true
                },
            ),
            navController = navController,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "otpauth://totp/.*?secret={$SECRET}&issuer={$ISSUER}&.*"
                },
            ),
        )
    }
}

private fun NavGraphBuilder.home(
    route: String,
    navController: NavController,
    onDrawerScreenDestination: (DrawerScreenDestination) -> Unit,
    onBottomBarDestination: (BottomNavigationDestination) -> Unit,
) = composable(route = route) {
    val viewModel = hiltViewModel<HomeViewModel>()
    HomeScreen(
        viewModel = viewModel,
        onBottomBarDestinationClick = onBottomBarDestination,
        onDrawerScreenDestinationClick = onDrawerScreenDestination,
    )
}

private fun NavGraphBuilder.totp(
    route: String,
    navController: NavController,
    onDrawerScreenDestination: (DrawerScreenDestination) -> Unit,
    onBottomBarDestination: (BottomNavigationDestination) -> Unit,
) = composable(route = route) {
    val viewModel = hiltViewModel<TotpViewModel>()
    TotpScreen(
        viewModel = viewModel,
        onDrawerScreenDestinationClick = onDrawerScreenDestination,
        onBottomBarDestinationClick = onBottomBarDestination,
        onAddTotpClick = { navController.navigateToAddTotp() },
    )
}

private fun NavGraphBuilder.login(
    route: String,
    navController: NavController,
) = composable(route = route) {
    val viewModel = hiltViewModel<LoginViewModel>()
    LoginScreen(
        viewModel = viewModel,
        onNavigateToHome = { navController.navigateToHome() },
    )
}

private fun NavGraphBuilder.logout(
    route: String,
    navController: NavController,
) = dialog(route = route) {
    val viewModel = hiltViewModel<LogoutViewModel>()
    LogoutScreen(
        viewModel = viewModel,
        onClose = { navController.popBackStack() },
        navigateToLogin = { navController.navigateToLogin() },
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
            navController.navigateToTotp()
        },
        onClose = { navController.popBackStack() },
    )
}
