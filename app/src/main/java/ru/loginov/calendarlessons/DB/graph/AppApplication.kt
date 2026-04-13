package ru.loginov.calendarlessons.DB.graph

import android.app.Application
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
@AndroidEntryPoint
class AppApplication: Application() {

    override fun onCreate() {
        super.onCreate()

    }
}

