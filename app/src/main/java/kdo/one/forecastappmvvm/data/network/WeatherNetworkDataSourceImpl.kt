package kdo.one.forecastappmvvm.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kdo.one.forecastappmvvm.data.network.response.CurrentWeatherResponse
import kdo.one.forecastappmvvm.internal.NoConnectivityException

class WeatherNetworkDataSourceImpl(
    private val apiService: WeatherApiService

) : WeatherNetworkDataSource {

    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()

    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String) {
        try {
            val fetchCurrentWeather = apiService.getCurrentWeather(location).await()
            _downloadedCurrentWeather.postValue(fetchCurrentWeather)

        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection!", e)
        }
    }
}