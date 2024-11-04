package br.com.fiap.irrigaapp.ui.components.previsaotempo

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness2
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Brightness6
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Umbrella
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import br.com.fiap.irrigaapp.data.model.WeatherResponse
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TemperaturasHoraAHora(weather: WeatherResponse) {
    val sunriseMillis = weather.current.sunrise * 1000L
    val sunsetMillis = weather.current.sunset * 1000L

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(weather.hourly.take(24)) { hourlyData ->
            val horario = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(hourlyData.dt * 1000))
            val isHourlyDayTime = (hourlyData.dt * 1000L) in sunriseMillis..sunsetMillis
            val hourlyWeatherCondition = hourlyData.weather[0].main

            Log.d("WeatherCondition", "Condition: $hourlyWeatherCondition")

            val hourlyWeatherIcon: ImageVector = if (isHourlyDayTime) {
                when (hourlyWeatherCondition) {
                    "Clear" -> Icons.Filled.Brightness4
                    "Clouds" -> Icons.Filled.Cloud
                    "Rain" -> Icons.Filled.Umbrella
                    else -> Icons.Filled.Brightness6
                }
            } else {
                when (hourlyWeatherCondition) {
                    "Clear" -> Icons.Filled.Brightness2
                    "Clouds" -> Icons.Filled.Cloud
                    "Rain" -> Icons.Filled.Umbrella
                    else -> Icons.Filled.Brightness2
                }
            }

            val hourlyIconColor: Color = when (hourlyWeatherCondition) {
                "Clear" -> if (isHourlyDayTime) Color.Yellow else Color.LightGray
                "Clouds" -> Color.Gray
                "Rain" -> Color.Blue
                else -> Color.White
            }

            DiaTemperaturaItem(horario, hourlyData.temp, hourlyWeatherIcon, hourlyIconColor)
        }
    }
}
