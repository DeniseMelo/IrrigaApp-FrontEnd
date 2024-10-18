package br.com.fiap.irrigaapp.data.remote.wheatherApi

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherRetrofitClient {
    private const val BASE_URL = "https://api.openweathermap.org/"

    // Configuração do Gson com a política de nomenclatura
    private val gson: Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: OpenWeatherMapService by lazy {
        retrofit.create(OpenWeatherMapService::class.java)
    }
}
