package rs.raf.rafeisen.navigation

import androidx.navigation.NavController

fun NavController.navigateToLogin() = navigate("login")
fun NavController.navigateToLogout() = navigate("logout")
fun NavController.navigateToAddTotp() = navigate("totp")
fun NavController.navigateToHome() = navigate("home")
fun NavController.navigateToLandingPage() = navigate("landing")
