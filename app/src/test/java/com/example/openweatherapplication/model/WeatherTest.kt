package com.example.openweatherapplication.model

import com.google.gson.Gson
import org.junit.Assert.*
import org.junit.Test

class WeatherTest{
    private val gson = Gson()

    @Test
    fun testWeatherSerialization() {
        val weather = Weather(
            base = "base",
            clouds = Clouds(),
            cod = 123,
            coord = Coord(),
            dt = 456,
            id = 789,
            main = Main(),
            name = "City",
            sys = Sys(),
            timezone = 3600,
            visibility = 5000,
            weather = listOf(WeatherX()),
            wind = Wind()
        )

        val json = gson.toJson(weather)
        val deserializedWeather = gson.fromJson(json, Weather::class.java)

        assertEquals(weather.base, deserializedWeather.base)
        assertEquals(weather.cod, deserializedWeather.cod)
        assertEquals(weather.dt, deserializedWeather.dt)
        assertEquals(weather.id, deserializedWeather.id)
        assertEquals(weather.name, deserializedWeather.name)
        assertEquals(weather.timezone, deserializedWeather.timezone)
        assertEquals(weather.visibility, deserializedWeather.visibility)

        // Compare nested objects as well
        assertEquals(weather.clouds, deserializedWeather.clouds)
        assertEquals(weather.coord, deserializedWeather.coord)
        assertEquals(weather.main, deserializedWeather.main)
        assertEquals(weather.sys, deserializedWeather.sys)
        assertEquals(weather.weather, deserializedWeather.weather)
        assertEquals(weather.wind, deserializedWeather.wind)
    }
}