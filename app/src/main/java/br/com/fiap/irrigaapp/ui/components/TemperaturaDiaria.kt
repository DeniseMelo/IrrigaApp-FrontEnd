package br.com.fiap.irrigaapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import br.com.fiap.irrigaapp.data.model.WeatherResponse
import br.com.fiap.irrigaapp.viewmodel.PrevisaoTempoViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TemperaturaDiaria(
    viewModel: PrevisaoTempoViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var weatherResponse by remember { mutableStateOf<WeatherResponse?>(null) }
    var latitude by remember { mutableStateOf<Double?>(null) }
    var longitude by remember { mutableStateOf<Double?>(null) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Obtém a localização do usuário através da ViewModel
    LaunchedEffect(Unit) {
        viewModel.fetchUserLocation(context) { lat, lon ->
            latitude = lat
            longitude = lon

            // Chama a API do clima quando a localização é obtida
            scope.launch {
                viewModel.fetchWeatherData(lat, lon) { response ->
                    weatherResponse = response
                }
            }
        }
    }

    weatherResponse?.let { weather ->
        // Data e hora atuais
        val currentDate = SimpleDateFormat("EEEE, dd MMMM", Locale.getDefault()).format(Date())
        val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

        // Condição climática atual
        val currentTemp = weather.hourly[0].temp
        val weatherCondition = weather.hourly[0].weather[0].main

        // Obter o tempo atual em milissegundos
        val currentTimeMillis = System.currentTimeMillis()

        // Converter sunrise e sunset para milissegundos
        val sunriseMillis = weather.current.sunrise * 1000L
        val sunsetMillis = weather.current.sunset * 1000L

        // Determinar se é dia ou noite
        val isDayTime = currentTimeMillis in sunriseMillis..sunsetMillis

        // Definir o ícone baseado em isDayTime
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

        // Cor dos ícones
        val iconColor: Color = when (weatherCondition) {
            "Clear" -> if (isDayTime) Color.Yellow else Color.LightGray
            "Clouds" -> Color.Gray
            "Rain" -> Color.Blue
            else -> Color.White
        }

        // Utilize LazyColumn em vez de Column
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.LightGray)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                // Exibe a temperatura atual e o ícone correspondente
                Icon(
                    imageVector = weatherIcon,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = iconColor
                )
                Text(
                    text = "${currentTemp.toInt()}°C",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Exibe a data e hora atuais
                Text(text = currentDate, style = MaterialTheme.typography.labelLarge)
                Text(text = currentTime, style = MaterialTheme.typography.labelMedium)

                Spacer(modifier = Modifier.height(16.dp))

                // Exibe as temperaturas hora a hora
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(weather.hourly.take(24)) { hourlyData ->
                        val horario = SimpleDateFormat(
                            "HH:mm",
                            Locale.getDefault()
                        ).format(Date(hourlyData.dt * 1000))

                        // Determinar se é dia ou noite para cada hora
                        val hourlyTimeMillis = hourlyData.dt * 1000L
                        val isHourlyDayTime = hourlyTimeMillis in sunriseMillis..sunsetMillis

                        val hourlyWeatherCondition = hourlyData.weather[0].main

                        val hourlyWeatherIcon: ImageVector = if (isHourlyDayTime) {
                            when (hourlyWeatherCondition) {
                                "Clear" -> Icons.Default.WbSunny
                                "Clouds" -> Icons.Default.Cloud
                                "Rain" -> Icons.Default.Umbrella
                                else -> Icons.Default.WbSunny
                            }
                        } else {
                            when (hourlyWeatherCondition) {
                                "Clear" -> Icons.Default.Brightness2
                                "Clouds" -> Icons.Default.Cloud
                                "Rain" -> Icons.Default.Umbrella
                                else -> Icons.Default.Brightness2
                            }
                        }

                        val hourlyIconColor: Color = when (hourlyWeatherCondition) {
                            "Clear" -> if (isHourlyDayTime) Color.Yellow else Color.LightGray
                            "Clouds" -> Color.Gray
                            "Rain" -> Color.Blue
                            else -> Color.White
                        }

                        DiaTemperaturaItem(
                            horario,
                            hourlyData.temp,
                            hourlyWeatherIcon,
                            hourlyIconColor
                        )
                    }
                }
            }

            // Adiciona um Spacer flexível
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Adiciona o botão de "Previsão Para os Próximos Dias"
            item {
                Button(
                    onClick = {
                        // Ação ao clicar no botão (ex: navegar para outra tela)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6FAFDD) // Define a cor de fundo do botão
                    )
                ) {
                    Text("Previsão Para os Próximos Dias")
                }
            }
        }
    } ?: run {
        // Exibe um indicador de carregamento centralizado
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun DiaTemperaturaItem(
    horario: String,
    temp: Double,
    icon: ImageVector,
    iconColor: Color
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .width(80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(40.dp),
            tint = iconColor
        )
        Text(text = horario, style = MaterialTheme.typography.labelLarge)
        Text(text = "${temp.toInt()}°C", style = MaterialTheme.typography.bodyLarge)
    }
}
