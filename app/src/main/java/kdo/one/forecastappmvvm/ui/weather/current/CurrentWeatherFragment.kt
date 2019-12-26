package kdo.one.forecastappmvvm.ui.weather.current

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kdo.one.forecastappmvvm.R
import kdo.one.forecastappmvvm.internal.glide.GlideApp
import kdo.one.forecastappmvvm.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

@SuppressLint("SetTextI18n")
class CurrentWeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein by kodein()
    private val factory: CurrentWeatherViewModelFactory by instance()
    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(CurrentWeatherViewModel::class.java)
        bindUi()
    }

    private fun bindUi() = launch {
        val currentWeather = viewModel.weather.await()
        val weatherLocation = viewModel.weatherLocation.await()

        weatherLocation.observe(this@CurrentWeatherFragment, Observer { location ->
            if (location == null) return@Observer
            updateLocation(location.name)
        })

        currentWeather.observe(this@CurrentWeatherFragment, Observer {
            if (it == null) return@Observer

            group_loading.visibility = View.GONE
            updateDateToToday()
            updateTemperatures(it.temperature, it.feelslike)
            updateCondition(it.weatherDescriptions[0])
            updatePrecipitation(it.precip)
            updateWind(it.windDir, it.windSpeed)
            updateVisibility(it.visibility)

            GlideApp.with(this@CurrentWeatherFragment)
                .load(it.weatherIcons[0])
                .into(imageView_condition_icon)
        })
    }

    private fun updateLocation(location: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDateToToday() {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }

    private fun updateTemperatures(temp: Int, feelsLike: Int) {
        val unit = "°C"
        textView_temperature.text = "$temp$unit"
        textView_feels_like_temperature.text = "Feels like $feelsLike$unit"
    }

    private fun updateCondition(condition: String) {
        textView_condition.text = condition
    }

    private fun updatePrecipitation(volume: Double) {
        val unit = "mm"
        textView_precipitation.text = "Precipitation: $volume $unit"
    }

    private fun updateWind(windDirection: String, windSpeed: Double) {
        val unit = "kph"
        textView_wind.text = "Wind: $windDirection, $windSpeed $unit"
    }

    private fun updateVisibility(distance: Int) {
        val unit = "km"
        textView_visibility.text = "Visibility: $distance $unit"
    }
}
