package br.com.fiap.irrigaapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import br.com.fiap.irrigaapp.ui.components.FundoOpaco
import br.com.fiap.irrigaapp.ui.components.Menu
import br.com.fiap.irrigaapp.ui.components.previsaoproximosdias.PrevisaoProximosDias
import br.com.fiap.irrigaapp.viewmodel.PrevisaoTempoViewModel

@Composable
fun PrevisaoProximosDiasScreen(
    navController: NavHostController,
    menuExpandido: Boolean,
    onBackgroundClick: () -> Unit
) {
    val viewModel: PrevisaoTempoViewModel = viewModel()
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Menu(navController = navController)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = if (!menuExpandido) 60.dp else 0.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PrevisaoProximosDias(viewModel = viewModel, context = context)
        }

        // Fundo opaco
        if (menuExpandido) {
            FundoOpaco(onClick = onBackgroundClick)
        }
    }
}
