package com.example.openweatherapplication.network

import com.example.openweatherapplication.BuildConfig
import com.example.openweatherapplication.model.Weather
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class MyRetrofit {
    companion object{
        fun getRetrofit() : Retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()

        fun getService() : MyWeatherService = getRetrofit().create(MyWeatherService::class.java)
    }
}

interface MyWeatherService {
        @GET("weather") //"weather?q={city}&appid={api key}"
            suspend fun getCityWeather(@Query("q") myCity: String, @Query("appid") appID: String): Response<Weather>
    }
