package br.com.fiap.irrigaapp.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.fiap.irrigaapp.ui.screens.PrevisaoProximosDiasScreen
import br.com.fiap.irrigaapp.ui.screens.PrevisaoTempo
import br.com.fiap.irrigaapp.ui.screens.WiFiScreen
import br.com.fiap.irrigaapp.ui.screens.LoginScreen
import br.com.fiap.irrigaapp.ui.screens.RegisterScreen
import br.com.fiap.irrigaapp.ui.screens.SensoresScreen
import br.com.fiap.irrigaapp.viewmodel.LoginViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    menuExpandido: Boolean,
    onBackgroundClick: () -> Unit
) {
    NavHost(navController = navController, startDestination = "login") {

        composable("login") {
            LoginScreen(
                navController = navController,
                viewModel = loginViewModel,
                onLoginSuccess = { navController.navigate("sensores") },
                onRegisterClick = { navController.navigate("register") }
            )
        }

        composable("register") {
            RegisterScreen(
                navController = navController,
                onRegisterSuccess = { navController.navigate("login") },
                onLoginClick = { navController.navigate("login") }
            )
        }

        composable("sensores") {
            SensoresScreen(navController = navController)
        }

        composable("PrevisaoTempo") {
            PrevisaoTempo(
                navController = navController,
                menuExpandido = menuExpandido,
                onBackgroundClick = onBackgroundClick
            )
        }

        composable("wifiScreen") {
            WiFiScreen(
                navController = navController,
                menuExpandido = menuExpandido,
                onBackgroundClick = onBackgroundClick
            )
        }

        composable("PrevisaoProximosDias") {
            Log.d("AppNavigation", "rota encontrada")
            PrevisaoProximosDiasScreen(
                navController = navController,
                menuExpandido = menuExpandido,
                onBackgroundClick = onBackgroundClick
            )
        }
    }
}
