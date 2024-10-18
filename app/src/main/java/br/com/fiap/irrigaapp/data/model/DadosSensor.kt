package br.com.fiap.irrigaapp.data.model

data class DadosSensor(
    val id: String,
    val localizacao: String,
    val statusRele: String,
    val nivelUmidade: Float,
    val latitude: Double,
    val longitude: Double,
    val temperatura: Float? = null
)
