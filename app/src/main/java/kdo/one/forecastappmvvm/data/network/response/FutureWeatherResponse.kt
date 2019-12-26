package kdo.one.forecastappmvvm.data.network.response


import com.google.gson.annotations.SerializedName
import kdo.one.forecastappmvvm.data.db.entity.Location

data class FutureWeatherResponse(
    val forecast: Forecast,
    val location: Location
)