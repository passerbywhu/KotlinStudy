package com.passerbywhu.kotlinstudy.forecast

import android.util.Log
import java.net.URL

class Request(val url: String) {
    public fun run() {
        val forecastJsonStr = URL(url).readText()
        Log.d(javaClass.simpleName, forecastJsonStr)
    }
}