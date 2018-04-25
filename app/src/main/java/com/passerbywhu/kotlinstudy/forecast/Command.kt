package com.passerbywhu.kotlinstudy.forecast

interface Command<T> {
    fun execute() : T
}