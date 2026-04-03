package ru.loginov.calendarlessons.DB.graph

import android.content.Context
import ru.loginov.calendarlessons.DB.database.lessonDatabase
import ru.loginov.calendarlessons.DB.repository.Repository

object graph {

    lateinit var db: lessonDatabase
        private set

    val repository by lazy {
        Repository(
            userDao = db.userDao(),
            typeLessonDao = db.typeDao(),
            lessonDao = db.lessonDao(),
        )
    }

    fun provide(context: Context){
        db = lessonDatabase.getDatabase(context)
    }


}