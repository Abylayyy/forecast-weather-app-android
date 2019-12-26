package kdo.one.forecastappmvvm.data.network.response


import com.google.gson.annotations.SerializedName

data class X20191230(
    val avgtemp: Int,
    val date: String,
    @SerializedName("date_epoch")
    val dateEpoch: Int,
    val maxtemp: Int,
    val mintemp: Int,
    val sunhour: Int,
    val totalsnow: Int,
    @SerializedName("uv_index")
    val uvIndex: Int
)