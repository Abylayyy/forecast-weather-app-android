package kdo.one.forecastappmvvm.data.network.response

import com.google.gson.annotations.SerializedName
import kdo.one.forecastappmvvm.data.db.entity.CurrentWeatherEntry
import kdo.one.forecastappmvvm.data.db.entity.Location
import kdo.one.forecastappmvvm.data.db.entity.Request


data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,
    val location: Location,
    val request: Request
)