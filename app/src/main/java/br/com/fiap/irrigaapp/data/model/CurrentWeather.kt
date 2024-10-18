package br.com.fiap.irrigaapp.data.model

data class CurrentWeather(
    val temp: Double,
    val humidity: Int,
    val weather: List<WeatherDescription>,
    val sunrise: Long,
    val sunset: Long
)