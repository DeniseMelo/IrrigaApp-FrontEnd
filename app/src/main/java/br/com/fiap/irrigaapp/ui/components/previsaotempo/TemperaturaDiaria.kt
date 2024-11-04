package br.com.fiap.irrigaapp.ui.components.previsaotempo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import br.com.fiap.irrigaapp.data.model.WeatherResponse
import br.com.fiap.irrigaapp.ui.components.previsaoproximosdias.TemperaturaAtualHeader
import br.com.fiap.irrigaapp.viewmodel.PrevisaoTempoViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TemperaturaDiaria(modifier: Modifier = Modifier,
    viewModel: PrevisaoTempoViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
) {

    var weatherResponse by remember { mutableStateOf<WeatherResponse?>(null) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.fetchUserLocation(context) { lat, lon ->
            scope.launch { viewModel.fetchWeatherData(lat, lon) { response -> weatherResponse = response } }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
                // Tela principal de TemperaturaDiaria com informações de clima
                weatherResponse?.let { weather ->
                    val currentDate = SimpleDateFormat("EEEE, dd MMMM", Locale.getDefault()).format(Date())
                    val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.LightGray)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        item {
                            TemperaturaAtualHeader(
                                currentTemp = weather.hourly[0].temp,
                                currentDate = currentDate,
                                currentTime = currentTime,
                                isDayTime = System.currentTimeMillis() in (weather.current.sunrise * 1000L)..(weather.current.sunset * 1000L),
                                weatherCondition = weather.hourly[0].weather[0].main
                            )
                        }

                        item {
                            TemperaturasHoraAHora(weather)
                        }

                    }
                } ?: run {
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


}


