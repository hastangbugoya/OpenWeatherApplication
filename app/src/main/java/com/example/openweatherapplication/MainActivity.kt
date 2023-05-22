package com.example.openweatherapplication

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.openweatherapplication.databinding.ActivityMainBinding
import com.example.openweatherapplication.model.MySharedPreference
import com.example.openweatherapplication.viewmodel.MainForecastViewModel

class MainActivity : AppCompatActivity() {
    private val REQUEST_LOCATION_PERMISSION = 42

    private val inputMethodManager: InputMethodManager by lazy {
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

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
            binding.temperatureText.text = ""
            binding.descriptionText.text = ""
            binding.weatherIcon.setImageDrawable(null)
            it?.let {
                mySharedPreference.saveCity(it.name)
                binding.temperatureText.text = String.format("%.2fF°", it.main?.temp?.toFahrenheitFromKelvin() ?: 0.0)
                Log.d("Meow","https://openweathermap.org/img/wn/${it.weather?.get(0)?.icon.toString().trim()}@2x.png")
                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/${it.weather?.get(0)?.icon.toString().trim()}@2x.png")
                    .placeholder(R.drawable.baseline_image_not_supported_48)
                    .into(binding.weatherIcon)
                binding.descriptionText.setText(it.weather?.get(0)?.description ?: "")
                binding.weatherIcon
            } ?: run {
                Toast.makeText(this@MainActivity, "Error encountered in retrieving city weather data",Toast.LENGTH_LONG).show()
            }
            hideTheKeyBoard()
        }

        binding.edittextCity.setText(mySharedPreference.getCity() ?: "")

        binding.buttonGetIt.setOnClickListener {
            forecastViewModel.getCityData(binding.edittextCity.text.toString().trim())
        }

        binding.edittextCity.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                forecastViewModel.getCityData(binding.edittextCity.text.toString().trim())
                true
            } else {
                false
            }
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
        hideTheKeyBoard()
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_LOCATION_PERMISSION
        )
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        when (requestCode) {
//            REQUEST_LOCATION_PERMISSION -> {
//                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // Permission granted, proceed with location-related operations
//                    // ...
//                } else {
//                    Toast.makeText(this,"Location permission needed",Toast.LENGTH_LONG).show()
//                }
//            }
//            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        }
//    }

    private fun hideTheKeyBoard() {
        inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }
}