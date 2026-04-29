package ru.loginov.calendarlessons.DB.DI_database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.loginov.calendarlessons.DB.DAO.LessonDao
import ru.loginov.calendarlessons.DB.DAO.Lessons_slotDao
import ru.loginov.calendarlessons.DB.DAO.TypeLessonDao
import ru.loginov.calendarlessons.DB.DAO.UserDao
import ru.loginov.calendarlessons.DB.converters.DateConverter
import ru.loginov.calendarlessons.DB.tables.Lessons
import ru.loginov.calendarlessons.DB.tables.Lessons_slot
import ru.loginov.calendarlessons.DB.tables.TypeLessons
import ru.loginov.calendarlessons.DB.tables.User

@Database(
    entities = [User::class, Lessons::class, TypeLessons::class, Lessons_slot::class],
    version = 4,
    exportSchema = false,
)
@TypeConverters(DateConverter::class)
abstract class lessonDatabase: RoomDatabase(){
    abstract fun userDao(): UserDao
    abstract fun typeDao(): TypeLessonDao
    abstract fun lessonDao(): LessonDao

    abstract fun Lessons_slotDao(): Lessons_slotDao
}
