package br.com.fiap.irrigaapp.viewmodel



import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.irrigaapp.BuildConfig
import br.com.fiap.irrigaapp.data.model.WeatherResponse
import br.com.fiap.irrigaapp.data.remote.userLocation.getUserLocation
import br.com.fiap.irrigaapp.data.remote.wheatherApi.WeatherRetrofitClient
import kotlinx.coroutines.launch

class PrevisaoProximosDiasViewModel : ViewModel() {

    fun fetchWeatherData(
        context: Context,
        onResult: (WeatherResponse?) -> Unit
    ) {
        fetchUserLocation(context) { latitude, longitude ->
            fetchDailyWeatherData(latitude, longitude, onResult)
        }
    }

    private fun fetchDailyWeatherData(
        latitude: Double,
        longitude: Double,
        onResult: (WeatherResponse?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                // Chama a API usando Retrofit para previsão de 8 dias
                val response = WeatherRetrofitClient.weatherApiService.getDailyWeatherData(
                    latitude = latitude,
                    longitude = longitude,
                    apiKey = BuildConfig.OPENWEATHERMAPAPIKEY
                )

                // Passa o resultado para a UI
                onResult(response)
            } catch (e: Exception) {
                e.printStackTrace()
                onResult(null)
            }
        }
    }

    // Função para obter a localização do usuário utilizando a função já existente
    private fun fetchUserLocation(context: Context, onLocationReceived: (Double, Double) -> Unit) {
        getUserLocation(context) { latitude, longitude ->
            onLocationReceived(latitude, longitude)
        }
    }
}
