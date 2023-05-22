package com.example.openweatherapplication.model


import com.google.gson.annotations.SerializedName

data class Sys(
    @SerializedName("country")
    var country: String? = "",
    @SerializedName("id")
    var id: Int? = 0,
    @SerializedName("sunrise")
    var sunrise: Int? = 0,
    @SerializedName("sunset")
    var sunset: Int? = 0,
    @SerializedName("type")
    var type: Int? = 0
)