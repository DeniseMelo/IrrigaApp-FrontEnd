package br.com.fiap.irrigaapp.data.remote.wheatherApi

import br.com.fiap.irrigaapp.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherMapService {
    @GET("data/3.0/onecall")
    suspend fun getWeatherData(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("exclude") exclude: String = "minutely",
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric",
        @Query("lang") language: String = "pt_br"
    ): WeatherResponse
}