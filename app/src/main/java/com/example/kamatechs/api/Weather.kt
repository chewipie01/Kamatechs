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
import java.text.SimpleDateFormat
import java.util.*

class Weather: AppCompatActivity() {
    private var address: TextView? = null
    private var updated: TextView? = null
    private var temp: TextView? = null
    private var temp_min: TextView? = null
    private var temp_max: TextView? = null
    private var humidity: TextView? = null
    private var pressure: TextView? = null
    private var wind: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        address = findViewById(R.id.country)
        updated = findViewById(R.id.updated)
        temp = findViewById(R.id.temp)
        temp_min  = findViewById(R.id.temp_min)
        temp_max = findViewById(R.id.temp_max)
        humidity = findViewById(R.id.humidity)
        pressure = findViewById(R.id.pressure)
        wind = findViewById(R.id.wind)
        findViewById<View> (R.id.data_button).setOnClickListener {
            getCurrentData()
        }
        val actionbar = supportActionBar
        actionbar!!.title = "Weather"
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

                    val location = weatherResponse.name + ", " + weatherResponse.sys!!.country
                    val tempe = "" + weatherResponse.main!!.temp + "°C"
                    val updatedAt = weatherResponse.dt.toLong()
                    val upd = "Updated at: "+ SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.ENGLISH).format(Date(updatedAt*1000))
                    val tempMin = "Min Temp: " + weatherResponse.main!!.temp_min + "°C"
                    val tempMax = "Max Temp: " + weatherResponse.main!!.temp_max + "°C"
                    val humid =  weatherResponse.main!!.humidity
                    val hpa =  weatherResponse.main!!.pressure
                    val speed = weatherResponse.wind!!.speed

                    address!!.text = location
                    updated!!.text = upd
                    temp!!.text = tempe
                    temp_min!!.text = tempMin
                    temp_max!!.text = tempMax
                    humidity!!.text = humid.toString()
                    pressure!!.text = hpa.toString()
                    wind!!.text = speed.toString()
                }
            }

            override fun onFailure (call: Call<WeatherResponse>, t: Throwable){
                address!!.text = t.message
                temp!!.text = t.message
                updated!!.text = t.message
                temp_min!!.text = t.message
                temp_max!!.text = t.message
                humidity!!.text = t.message
                pressure!!.text = t.message
                wind!!.text = t.message
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