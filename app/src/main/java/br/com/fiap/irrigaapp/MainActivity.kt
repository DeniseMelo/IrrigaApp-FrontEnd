package br.com.fiap.irrigaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import br.com.fiap.irrigaapp.ui.navigation.AppNavigation
import br.com.fiap.irrigaapp.viewmodel.LoginViewModel
import br.com.fiap.irrigaapp.viewmodel.LoginViewModelFactory
import com.mapbox.common.MapboxOptions

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Token público
        MapboxOptions.accessToken = "pk.eyJ1IjoiZGVuaXNlY21mIiwiYSI6ImNtMXozcXJtNjAyc2EybHBxNDF3cGV6cTcifQ.UyZB8Swr4DngD0GCB_Mt2g"

        setContent {
            val loginViewModel = ViewModelProvider(
                this,
                LoginViewModelFactory(application) // Usa o Application
            )[LoginViewModel::class.java]

            val navController = rememberNavController()
            var menuExpandido by remember { mutableStateOf(false) }

            // Inicia o AppNavigation e delega a navegação inicial com base no token
            AppNavigation(
                navController = navController,
                loginViewModel = loginViewModel,
                menuExpandido = menuExpandido,
                onBackgroundClick = { menuExpandido = false }
            )

            // Verifica se o token está presente e navega diretamente para "sensores" se o usuário já estiver autenticado
            val token = (application as MyApplication).tokenProvider.getToken()
            if (token != null) {
                navController.navigate("sensores") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }
    }
}
