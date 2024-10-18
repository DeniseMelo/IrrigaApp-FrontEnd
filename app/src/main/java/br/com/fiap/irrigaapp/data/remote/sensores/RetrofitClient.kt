package br.com.fiap.irrigaapp.data.remote.sensores

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://sua-api.com/"  // Substitua pela URL da sua API

    // Inicializa o Retrofit
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())  // Para converter JSON para objetos Kotlin
            .build()
    }

    // Exponha o serviço de API
    val apiService: SensorApiService by lazy {
        retrofit.create(SensorApiService::class.java)
    }
}
