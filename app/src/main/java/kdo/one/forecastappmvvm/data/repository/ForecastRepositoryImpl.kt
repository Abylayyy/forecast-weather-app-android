package kdo.one.forecastappmvvm.data.repository

import androidx.lifecycle.LiveData
import kdo.one.forecastappmvvm.data.db.CurrentWeatherDao
import kdo.one.forecastappmvvm.data.db.WeatherLocationDao
import kdo.one.forecastappmvvm.data.db.entity.CurrentWeatherEntry
import kdo.one.forecastappmvvm.data.db.entity.Location
import kdo.one.forecastappmvvm.data.network.WeatherNetworkDataSource
import kdo.one.forecastappmvvm.data.network.response.CurrentWeatherResponse
import kdo.one.forecastappmvvm.data.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherLocationDao: WeatherLocationDao,
    private val dataSource: WeatherNetworkDataSource,
    private val provider: LocationProvider
) : ForecastRepository {

    init {
        dataSource.downloadedCurrentWeather.observeForever {
            persistFetchedCurrentWeather(it)
        }
    }

    override suspend fun getCurrentWeather(): LiveData<CurrentWeatherEntry> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext currentWeatherDao.getWeatherMetric()
        }
    }

    override suspend fun getWeatherLocation(): LiveData<Location> {
        return withContext(Dispatchers.IO) {
            return@withContext weatherLocationDao.getLocation()
        }
    }

    private fun persistFetchedCurrentWeather(response: CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(response.currentWeatherEntry)
            weatherLocationDao.upsert(response.location)
        }
    }

    private suspend fun initWeatherData() {
        val lastWeatherLocation = weatherLocationDao.getLocation().value

        if (lastWeatherLocation == null || provider.hasLocationChanged(lastWeatherLocation)) {
            fetchCurrentWeather()
            return
        }

        if (isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime)) {
            fetchCurrentWeather()
        }
    }

    private suspend fun fetchCurrentWeather() {
        dataSource.fetchCurrentWeather(provider.getPreferredLocationString())
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}