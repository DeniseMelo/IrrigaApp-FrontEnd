package br.com.fiap.irrigaapp.ui.components.previsaotempo

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun DiaTemperaturaItem(
    horario: String,
    temp: Double,
    icon: ImageVector,
    iconColor: Color
) {
    Column(
        modifier = Modifier.padding(8.dp).width(80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(40.dp), tint = iconColor)
        Text(text = horario, style = MaterialTheme.typography.labelLarge)
        Text(text = "${temp.toInt()}°C", style = MaterialTheme.typography.bodyLarge)
    }
}
