package kdo.one.forecastappmvvm.data.network

import androidx.lifecycle.LiveData
import kdo.one.forecastappmvvm.data.network.response.CurrentWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(location: String)
}