package kdo.one.forecastappmvvm.data.provider

import kdo.one.forecastappmvvm.data.db.entity.Location

interface LocationProvider {
    suspend fun hasLocationChanged(lastWeatherLocation: Location): Boolean
    suspend fun getPreferredLocationString(): String
}