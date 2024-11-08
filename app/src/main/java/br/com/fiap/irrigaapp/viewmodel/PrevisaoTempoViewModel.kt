package br.com.fiap.irrigaapp.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.irrigaapp.BuildConfig
import kotlinx.coroutines.launch
import br.com.fiap.irrigaapp.data.model.WeatherResponse
import br.com.fiap.irrigaapp.data.remote.userLocation.getUserLocation
import br.com.fiap.irrigaapp.data.remote.wheatherApi.WeatherRetrofitClient

class PrevisaoTempoViewModel : ViewModel() {

    fun fetchWeatherData(
        latitude: Double,
        longitude: Double,
        onResult: (WeatherResponse?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                // Chama a API usando Retrofit para previsao de 24 horas
                val response = WeatherRetrofitClient.apiService.getWeatherData(
                    latitude = latitude,
                    longitude = longitude,
                    apiKey = BuildConfig.OPENWEATHERMAPAPIKEY
                )
                Log.d("PrevisaoTempoViewModel", "Resposta da API: $response") // Aqui imprimimos a resposta

                // Passa o resultado para a UI
                onResult(response)
            } catch (e: Exception) {
                e.printStackTrace()
                onResult(null)
            }
        }
    }


// Função para obter a localização do usuário utilizando a função já existente
fun fetchUserLocation(context: Context, onLocationReceived: (Double, Double) -> Unit) {
    getUserLocation(context) { latitude, longitude ->
        onLocationReceived(latitude, longitude)
    }
}
}