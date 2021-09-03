package com.homework.nasibullin

import android.app.Application
import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.homework.nasibullin.network.ApiService
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), Configuration.Provider {

    init {
        instance = this
    }
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

    }

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    val apiService: ApiService by lazy { ApiService.create() }

    companion object {
        lateinit var appContext: Context
            private set
        lateinit var instance:App
            private set
    }

    override fun getWorkManagerConfiguration() = Configuration.Builder()
        .setWorkerFactory(workerFactory)
        .setMinimumLoggingLevel(android.util.Log.DEBUG)
        .build()
}