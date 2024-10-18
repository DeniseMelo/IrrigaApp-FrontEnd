package br.com.fiap.irrigaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.rememberNavController

@Composable
fun SettingsScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Tela de Configurações", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))

        // Botão para voltar para a tela principal
        Button(onClick = { navController.navigate("main") }) {
            Text("Voltar para a Tela Principal")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    val navController = rememberNavController()  // Usa um NavController fictício
    SettingsScreen(navController = navController)
}
