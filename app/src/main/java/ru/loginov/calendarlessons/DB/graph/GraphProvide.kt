package ru.loginov.calendarlessons.DB.graph

import android.app.Application
import android.content.Context
import ru.loginov.calendarlessons.DB.database.lessonDatabase
import ru.loginov.calendarlessons.DB.repository.Repository

class GraphProvide: Application() {

    override fun onCreate() {
        super.onCreate()

        graph.provide(this)
    }
}

