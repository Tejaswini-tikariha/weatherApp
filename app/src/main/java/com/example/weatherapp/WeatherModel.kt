package com.example.weatherapp

data class WeatherModel(
    val location: Location,
    val current: Current
)

data class Location(
    val name: String,
    val country: String,
    val localtime: String
)

data class Current(
    val temp_c: Float,
    val condition: Condition,
    val humidity: Int,
    val wind_kph: Float,
    val uv: Float,
    val precip_mm: Float
)

data class Condition(
    val text: String,
    val icon: String
)
