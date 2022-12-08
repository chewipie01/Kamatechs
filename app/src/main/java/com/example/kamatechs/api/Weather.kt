package com.example.kamatechs.api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.kamatechs.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class Weather: AppCompatActivity() {
    private var weatherData: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        weatherData = findViewById(R.id.textView)
        findViewById<View> (R.id.data_button).setOnClickListener {
            getCurrentData()
        }
        val actionbar = supportActionBar
        actionbar!!.title = "Weather API"
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    private fun getCurrentData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory (GsonConverterFactory.create())
            .build()
        val service = retrofit.create(WeatherService::class.java)
        val call = service.getCurrentWeatherData(lat, lon, AppId)
        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (response.code() == 200) {
                    val weatherResponse = response.body()!!

                    val stringBuilder = "Country: " +
                            weatherResponse.sys!!.country +
                            "\n" +
                            "Name: " +
                            weatherResponse.name +
                            "\n" +
                            "Temperature (°C): " +
                            weatherResponse.main!!.temp +
                            "\n" +
                            "Temperature (Min): " +
                            weatherResponse.main!!.temp_min +
                            "\n" +
                            "Temperature (Max): " +
                            weatherResponse.main!!.temp_max +
                            "\n" +
                            "Humidity (%): " +
                            weatherResponse.main!!.humidity +
                            "\n" +
                            "Pressure (hPa): " +
                            weatherResponse.main!!.pressure

                    weatherData!!.text = stringBuilder
                }
            }

            override fun onFailure (call: Call<WeatherResponse>, t: Throwable){
                weatherData!!.text = t.message
            }
        })
    }
    companion object {
        var BaseUrl = "https://api.openweathermap.org/"
        var AppId = "f813e644951811d2f6ccee45c8492336"
        var lat = "15.44125"
        var lon = "120.72863"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
