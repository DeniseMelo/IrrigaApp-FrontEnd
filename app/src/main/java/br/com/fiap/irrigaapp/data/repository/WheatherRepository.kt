package br.com.fiap.irrigaapp.data.repository

import br.com.fiap.irrigaapp.data.model.WeatherResponse
import br.com.fiap.irrigaapp.data.remote.wheatherApi.WeatherRetrofitClient
import android.content.Context
import android.util.Log
import br.com.fiap.irrigaapp.BuildConfig


class WeatherRepository(private val context: Context) {
    private val weatherService = WeatherRetrofitClient.apiService

    suspend fun getWeatherData(latitude: Double, longitude: Double): WeatherResponse? {
        return try {
            Log.d("WeatherRepository", "Buscando dados de clima para Latitude: $latitude, Longitude: $longitude")
            val response = weatherService.getWeatherData(
                latitude = latitude,
                longitude = longitude,
                apiKey = BuildConfig.OPENWEATHERMAPAPIKEY
            )
            Log.d("WeatherRepository", "Dados de clima recebidos com sucesso: $response")
            response
        } catch (e: Exception) {
            Log.e("WeatherRepository", "Erro ao buscar dados de clima", e)
            null
        }
    }
}

