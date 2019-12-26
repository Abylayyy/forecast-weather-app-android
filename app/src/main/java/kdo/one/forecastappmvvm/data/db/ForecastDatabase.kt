package kdo.one.forecastappmvvm.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kdo.one.forecastappmvvm.data.db.converter.DataConverter
import kdo.one.forecastappmvvm.data.db.entity.CurrentWeatherEntry
import kdo.one.forecastappmvvm.data.db.entity.Location

@Database(
    entities = [CurrentWeatherEntry::class, Location::class],
    version = 1
)
@TypeConverters(DataConverter::class)
abstract class ForecastDatabase : RoomDatabase() {
    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun weatherLocationDao(): WeatherLocationDao

    companion object {
        @Volatile private var instance: ForecastDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                ForecastDatabase::class.java,
                "forecast.db")
                .build()
    }
}