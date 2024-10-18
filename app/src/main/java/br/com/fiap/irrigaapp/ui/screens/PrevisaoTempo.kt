package br.com.fiap.irrigaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.fiap.irrigaapp.ui.components.FundoOpaco
import br.com.fiap.irrigaapp.ui.components.MapLeaflet
import br.com.fiap.irrigaapp.ui.components.TemperaturaDiaria

@Composable
fun PrevisaoTempo(menuExpandido: Boolean, onBackgroundClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 50.dp)
        ) {

            MapLeaflet()
            TemperaturaDiaria()
        }

        //fundo opaco
        if (menuExpandido) {
            FundoOpaco(onClick = onBackgroundClick)
        }
    }
}
