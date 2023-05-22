package com.example.openweatherapplication

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import com.example.openweatherapplication.databinding.ActivityMainBinding
import com.example.openweatherapplication.viewmodel.MainForecastViiewModel
import com.example.openweatherapplication.views.WeatherDetailsFragment

class MainActivity : AppCompatActivity() {
    private val REQUEST_LOCATION_PERMISSION = 42

    val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(LayoutInflater.from(this))
    }

    val forecastViewModel: MainForecastViiewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        forecastViewModel.myData.observe(this) {
            it?.let {
                supportFragmentManager.beginTransaction().add(
                    R.id.main_frame,
                    WeatherDetailsFragment()).commit()
            } ?: Toast.makeText(this@MainActivity, "Error encountered in retrieving city weather data",Toast.LENGTH_LONG).show()
        }

        binding.buttonGetIt.setOnClickListener {
            forecastViewModel.getCityData(binding.edittextCity.text.toString().trim())
        }


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