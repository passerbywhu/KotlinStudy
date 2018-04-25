package com.passerbywhu.kotlinstudy.forecast.command

import com.passerbywhu.kotlinstudy.forecast.Command
import com.passerbywhu.kotlinstudy.forecast.ForecastDataMapper
import com.passerbywhu.kotlinstudy.forecast.ForecastRequest
import com.passerbywhu.kotlinstudy.forecast.domain.ForecastList

class RequestForecastCommand(val zipCode: String) : Command<ForecastList> {
    override fun execute(): ForecastList {
        val forecastRequest = ForecastRequest(zipCode)
        return ForecastDataMapper().convertFromDataModel(forecastRequest.execute())
    }
}
