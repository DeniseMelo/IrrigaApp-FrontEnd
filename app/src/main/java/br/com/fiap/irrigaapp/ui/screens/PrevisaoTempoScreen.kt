package br.com.fiap.irrigaapp.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.fiap.irrigaapp.ui.components.FundoOpaco
import br.com.fiap.irrigaapp.ui.components.Menu
import br.com.fiap.irrigaapp.ui.components.previsaotempo.MapLeaflet
import br.com.fiap.irrigaapp.ui.components.previsaotempo.PrevisaoProximosDiasButton
import br.com.fiap.irrigaapp.ui.components.previsaotempo.TemperaturaDiaria

@Composable
fun PrevisaoTempo(
    navController: NavHostController,
    menuExpandido: Boolean,
    onBackgroundClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        Menu(navController = navController)
        //padding para o menu recolhido
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = if (!menuExpandido) 60.dp else 0.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                MapLeaflet(modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(0.dp))
                TemperaturaDiaria(modifier = Modifier.fillMaxWidth())
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botão na parte inferior
            PrevisaoProximosDiasButton(
                onClick = {
                    Log.d("button", "evento de click")
                    navController.navigate("PrevisaoProximosDias")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }

        // Fundo opaco sobreposto ao conteúdo principal quando o menu está expandido
        if (menuExpandido) {
            FundoOpaco(onClick = onBackgroundClick)
        }
    }
}
