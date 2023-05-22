package com.example.openweatherapplication.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Forecast(
    @SerializedName("city")
    val city: City?,
    @SerializedName("cnt")
    val cnt: Int?,
    @SerializedName("cod")
    val cod: String?,
    @SerializedName("list")
    val list: List<Forecasts>?,
    @SerializedName("message")
    val message: Int?
) : Serializable