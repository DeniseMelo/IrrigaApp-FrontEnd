package br.com.fiap.irrigaapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.fiap.irrigaapp.ui.components.FundoOpaco
import br.com.fiap.irrigaapp.ui.components.Menu
import br.com.fiap.irrigaapp.ui.components.sensores.Sensores

@Composable
fun SensoresScreen(navController: NavHostController) {
    var menuExpandido by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {

        Menu(navController = navController)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = if (!menuExpandido) 60.dp else 0.dp)
        ) {
            Sensores(
                menuExpandido = menuExpandido,
                onBackgroundClick = { menuExpandido = false }
            )
        }

        // Fundo opaco para fechar o menu ao clicar fora dele
        if (menuExpandido) {
            FundoOpaco(onClick = { menuExpandido = false })
        }
    }
}
