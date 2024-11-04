package br.com.fiap.irrigaapp.data.remote.wheatherApi

import br.com.fiap.irrigaapp.data.model.GeocodingResponse
import br.com.fiap.irrigaapp.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    // Endpoint para a previsão dos próximos 8 dias
    @GET("onecall")
    suspend fun getDailyWeatherData(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("exclude") exclude: String = "minutely,hourly",
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String
    ): WeatherResponse

    // Endpoint para obter coordenadas da cidade pelo nome
    @GET("geo/1.0/direct")
    suspend fun getCoordinatesByCityName(
        @Query("q") cityName: String,
        @Query("limit") limit: Int = 1,
        @Query("appid") apiKey: String
    ): List<GeocodingResponse>
}
