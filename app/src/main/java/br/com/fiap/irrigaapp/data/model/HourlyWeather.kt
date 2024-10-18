package br.com.fiap.irrigaapp.data.model

data class HourlyWeather(
    val dt: Long,
    val temp: Double,
    val feelsLike: Double,
    val pressure: Int,
    val humidity: Int,
    val dewpPoint: Double,
    val uvi: Double,
    val clouds: Int,
    val visibility: Int,
    val windSpeed: Double,
    val windDeg: Int,
    val weather: List<WeatherDescription>,
    val pop: Double
)

