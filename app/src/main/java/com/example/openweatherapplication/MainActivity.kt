package com.example.openweatherapplication

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.openweatherapplication.databinding.ActivityMainBinding
import com.example.openweatherapplication.model.MySharedPreference
import com.example.openweatherapplication.viewmodel.MainForecastViewModel

class MainActivity : AppCompatActivity() {
    private val REQUEST_LOCATION_PERMISSION = 42

    private val mySharedPreference: MySharedPreference by lazy {
        MySharedPreference(applicationContext)
    }

    val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(LayoutInflater.from(this))
    }

    val forecastViewModel: MainForecastViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        forecastViewModel.myData.observe(this) {
            Log.d("Meow", "Detect change")
            binding.temperatureText.text = ""
            it?.let {
                mySharedPreference.saveCity(it.name)
                binding.temperatureText.text = String.format("%.2fF°", it.main?.temp?.toFahrenheitFromKelvin() ?: 0.0)
                Log.d("Meow","https://openweathermap.org/img/wn/${it.weather?.get(0)?.icon.toString().trim()}@2x.png")
                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/${it.weather?.get(0)?.icon.toString().trim()}@2x.png")
                    .placeholder(R.drawable.baseline_error_24)
                    .into(binding.weatherIcon)
                binding.weatherIcon
            } ?: run {
                Toast.makeText(this@MainActivity, "Error encountered in retrieving city weather data",Toast.LENGTH_LONG).show()
            }
        }

        binding.edittextCity.setText(mySharedPreference.getCity() ?: "")

        binding.buttonGetIt.setOnClickListener {
            Log.d("Meow", "Clicky")
            forecastViewModel.getCityData(binding.edittextCity.text.toString().trim())
        }
    }

    fun Double.toFahrenheitFromKelvin(): Double {
        return (this - 273.15) * 9 / 5 + 32
    }

    override fun onResume() {
        super.onResume()
        val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
        val permissionStatus = ContextCompat.checkSelfPermission(this, locationPermission)
        if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_LOCATION_PERMISSION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_LOCATION_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, proceed with location-related operations
                    // ...
                } else {
                    Toast.makeText(this,"Location permission needed",Toast.LENGTH_LONG).show()
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}