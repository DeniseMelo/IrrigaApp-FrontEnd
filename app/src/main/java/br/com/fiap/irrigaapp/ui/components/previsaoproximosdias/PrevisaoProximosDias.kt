package br.com.fiap.irrigaapp.ui.components.previsaoproximosdias

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fiap.irrigaapp.data.model.DailyWeather
import br.com.fiap.irrigaapp.viewmodel.PrevisaoTempoViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PrevisaoProximosDias(
    viewModel: PrevisaoTempoViewModel = viewModel(),
    context: Context
) {
    var previsoes by remember { mutableStateOf<List<DailyWeather>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Busca a localização e a previsão ao iniciar
    LaunchedEffect(Unit) {
        viewModel.fetchUserLocation(context) { latitude, longitude ->
            viewModel.fetchWeatherData(latitude, longitude) { response ->
                isLoading = false
                if (response != null) {
                    previsoes = response.daily.take(8) // Limitar a 8 dias
                } else {
                    errorMessage = "Erro ao buscar dados."
                }
            }
        }
    }

    // Exibir Loading ou Mensagem de Erro
    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (errorMessage != null) {
        Text(text = errorMessage!!, color = MaterialTheme.colorScheme.error)
    } else {
        // Exibir a lista de previsões
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(previsoes) { dia ->
                PrevisaoDiaCard(previsao = dia)
            }
        }
    }
}

@Composable
fun PrevisaoDiaCard(previsao: DailyWeather) {

    val date = Date(previsao.dt * 1000) // Converter de segundos para milissegundos
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val formattedDate = dateFormat.format(date)
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF6FAFDD))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Data: $formattedDate",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
            Text(
                text = "Máx: ${previsao.temp.max}°C",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
            Text(
                text = "Min: ${previsao.temp.min}°C",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
            Text(
                text = "Chuva: ${(previsao.pop * 100).toInt()}%",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
            Text(
                text = "Umidade: ${previsao.humidity}% ",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
    }
}
