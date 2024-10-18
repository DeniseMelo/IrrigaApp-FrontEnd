package br.com.fiap.irrigaapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.irrigaapp.ui.screens.MainScreen
import br.com.fiap.irrigaapp.ui.screens.SettingsScreen
import br.com.fiap.irrigaapp.ui.screens.WiFiScreen

@Composable
fun AppNavigation(menuExpandido: Boolean, onBackgroundClick: () -> Unit) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(navController)
        }
        composable("settings") {
            SettingsScreen(navController)
        }

        composable("wifiScreen") {
            WiFiScreen(menuExpandido = menuExpandido, onBackgroundClick = onBackgroundClick)
        }
    }
}
