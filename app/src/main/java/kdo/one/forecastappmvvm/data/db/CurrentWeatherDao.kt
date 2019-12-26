package kdo.one.forecastappmvvm.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kdo.one.forecastappmvvm.data.db.entity.CURRENT_ID
import kdo.one.forecastappmvvm.data.db.entity.CurrentWeatherEntry

@Dao()
interface CurrentWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeatherEntry)

    @Query("SELECT * FROM CURRENT_WEATHER WHERE ID = $CURRENT_ID")
    fun getWeatherMetric(): LiveData<CurrentWeatherEntry>
}