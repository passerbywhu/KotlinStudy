package com.passerbywhu.kotlinstudy.forecast

import com.passerbywhu.kotlinstudy.forecast.data.Forecast
import com.passerbywhu.kotlinstudy.forecast.data.ForecastResult
import com.passerbywhu.kotlinstudy.forecast.domain.ForecastList
import java.text.DateFormat
import java.util.*
import com.passerbywhu.kotlinstudy.forecast.domain.Forecast as ModelForecast

class ForecastDataMapper {
    fun convertFromDataModel(forecast: ForecastResult) : ForecastList {
        return ForecastList(forecast.city.name, forecast.city.country, convertForecastListToDomain(forecast.list))
    }

    private fun convertForecastListToDomain(list: List<Forecast>): List<ModelForecast> {
        return list.map { convertForecastItemToDomain(it) }
    }

    private fun convertForecastItemToDomain(forecast: Forecast) : ModelForecast {
        return ModelForecast(convertDate(forecast.dt), forecast.weather[0].description, forecast.temp.max.toInt(), forecast.temp.min.toInt())
    }

    private fun convertDate(date: Long) : String {
        val df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
        return df.format(date * 1000)
    }
}