package br.com.fiap.irrigaapp.data.model

data class WeatherResponse(
    val current: CurrentWeather,
    val hourly: List<HourlyWeather>,
    val daily: List<DailyWeather>
)