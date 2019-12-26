package kdo.one.forecastappmvvm.data.repository

import androidx.lifecycle.LiveData
import kdo.one.forecastappmvvm.data.db.entity.CurrentWeatherEntry
import kdo.one.forecastappmvvm.data.db.entity.Location

interface ForecastRepository {
    suspend fun getCurrentWeather(): LiveData<CurrentWeatherEntry>
    suspend fun getWeatherLocation(): LiveData<Location>
}