package br.com.fiap.irrigaapp.data.remote.api

import br.com.fiap.irrigaapp.data.model.DadosSensor
import retrofit2.http.GET

interface SensorApiService {
    @GET("sensores")
    suspend fun buscarDadosSensores(): List<DadosSensor>
}
