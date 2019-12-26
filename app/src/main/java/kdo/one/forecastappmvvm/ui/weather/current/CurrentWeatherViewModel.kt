package kdo.one.forecastappmvvm.ui.weather.current

import androidx.lifecycle.ViewModel
import kdo.one.forecastappmvvm.data.repository.ForecastRepository
import kdo.one.forecastappmvvm.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository
) : ViewModel() {
    val weather by lazyDeferred() {
        forecastRepository.getCurrentWeather()
    }

    val weatherLocation by lazyDeferred {
        forecastRepository.getWeatherLocation()
    }
}
