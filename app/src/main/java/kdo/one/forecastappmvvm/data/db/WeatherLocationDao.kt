package kdo.one.forecastappmvvm.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kdo.one.forecastappmvvm.data.db.entity.Location
import kdo.one.forecastappmvvm.data.db.entity.WEATHER_LOCATION_ID

@Dao
interface WeatherLocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(location: Location)

    @Query("SELECT * FROM WEATHER_LOCATION WHERE ID = $WEATHER_LOCATION_ID")
    fun getLocation(): LiveData<Location>
}