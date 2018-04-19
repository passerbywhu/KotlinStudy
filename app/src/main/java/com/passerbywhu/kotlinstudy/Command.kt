package com.passerbywhu.kotlinstudy

interface Command<T> {
    fun execute() : T
}