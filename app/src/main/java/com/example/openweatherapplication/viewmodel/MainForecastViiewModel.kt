package com.example.openweatherapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openweatherapplication.BuildConfig
import com.example.openweatherapplication.model.Forecast
import com.example.openweatherapplication.network.MyRetrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainForecastViiewModel: ViewModel() {
    var myData = MutableLiveData<Forecast?>()

    fun getCityData(cityString: String) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                myData.postValue(MyRetrofit.getService().getCityWeather(cityString, BuildConfig.API_KEY).body())
            }
        } catch (e: Exception) {
            myData.value = null
        }
    }
}