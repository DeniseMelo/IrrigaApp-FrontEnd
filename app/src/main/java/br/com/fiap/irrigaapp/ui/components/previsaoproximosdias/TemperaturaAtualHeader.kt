package br.com.fiap.irrigaapp.ui.components.previsaoproximosdias

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight

@Composable
fun TemperaturaAtualHeader(
    currentTemp: Double,
    currentDate: String,
    currentTime: String,
    isDayTime: Boolean,
    weatherCondition: String
) {
    val weatherIcon: ImageVector = if (isDayTime) {
        when (weatherCondition) {
            "Clear" -> Icons.Default.WbSunny
            "Clouds" -> Icons.Default.Cloud
            "Rain" -> Icons.Default.Umbrella
            else -> Icons.Default.WbSunny
        }
    } else {
        when (weatherCondition) {
            "Clear" -> Icons.Default.Brightness2
            "Clouds" -> Icons.Default.Cloud
            "Rain" -> Icons.Default.Umbrella
            else -> Icons.Default.Brightness2
        }
    }

    val iconColor: Color = when (weatherCondition) {
        "Clear" -> if (isDayTime) Color.Yellow else Color.LightGray
        "Clouds" -> Color.Gray
        "Rain" -> Color.Blue
        else -> Color.White
    }

    Icon(imageVector = weatherIcon, contentDescription = null, modifier = Modifier.size(64.dp), tint = iconColor)
    Text(text = "${currentTemp.toInt()}°C", fontSize = 48.sp, fontWeight = FontWeight.Bold)

    Spacer(modifier = Modifier.height(8.dp))

    Text(text = currentDate, style = MaterialTheme.typography.labelLarge)
    Text(text = currentTime, style = MaterialTheme.typography.labelMedium)
}

