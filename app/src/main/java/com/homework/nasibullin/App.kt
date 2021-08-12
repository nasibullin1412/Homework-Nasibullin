package com.homework.nasibullin

import android.app.Application
import android.content.Context

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

    }

    companion object {
        lateinit var appContext: Context
    }
}