package br.com.fiap.irrigaapp.data.model



data class DailyWeather(
    val dt: Long,
    val sunrise: Long,
    val sunset: Long,
    val moonrise: Long,
    val moonset: Long,
    val moonPhase: Double,
    val temp: Temperature,
    val feelsLike: FeelsLike,
    val pressure: Int,
    val humidity: Int,
    val dewPoint: Double,
    val windSpeed: Double,
    val windDeg: Int,
    val windGust: Double?,
    val weather: List<WeatherDescription>,
    val clouds: Int,
    val pop: Double,
    val rain: Double?,
    val uvi: Double
)