package ru.loginov.calendarlessons.DB.graph

import android.app.Application
import android.content.Context
import ru.loginov.calendarlessons.DB.database.lessonDatabase
import ru.loginov.calendarlessons.DB.repository.Repository

class GraphProvide: Application() {

    val db: lessonDatabase by lazy {
        lessonDatabase.getDatabase(applicationContext)
    }

        val repository: Repository by lazy {
            Repository(
                userDao = db.userDao(),
                typeLessonDao = db.typeDao(),
                lessonDao = db.lessonDao(),
            )

    }


    override fun onCreate() {
        super.onCreate()


    }
}

