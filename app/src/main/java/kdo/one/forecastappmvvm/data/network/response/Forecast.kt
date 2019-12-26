package kdo.one.forecastappmvvm.data.network.response


import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("2019-12-26")
    val x20191226: X20191226,
    @SerializedName("2019-12-27")
    val x20191227: X20191227,
    @SerializedName("2019-12-28")
    val x20191228: X20191228,
    @SerializedName("2019-12-29")
    val x20191229: X20191229,
    @SerializedName("2019-12-30")
    val x20191230: X20191230,
    @SerializedName("2019-12-31")
    val x20191231: X20191231,
    @SerializedName("2020-01-01")
    val x20200101: X20200101
)