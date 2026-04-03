package ru.loginov.calendarlessons.DB.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import ru.loginov.calendarlessons.DB.DAO.LessonDao
import ru.loginov.calendarlessons.DB.DAO.TypeLessonDao
import ru.loginov.calendarlessons.DB.DAO.UserDao
import ru.loginov.calendarlessons.DB.converters.DateConverter
import ru.loginov.calendarlessons.DB.tables.Lessons
import ru.loginov.calendarlessons.DB.tables.TypeLessons
import ru.loginov.calendarlessons.DB.tables.User

@Database(
    entities = [User::class, Lessons::class, TypeLessons::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class lessonDatabase: RoomDatabase(){
    abstract fun userDao(): UserDao
    abstract fun typeDao(): TypeLessonDao
    abstract fun lessonDao(): LessonDao

    @OptIn(InternalCoroutinesApi::class)
    companion object{
        @Volatile
        var INSTANCE: lessonDatabase? = null
        fun getDatabase(context: Context):lessonDatabase{
            return INSTANCE?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        lessonDatabase::class.java,
                        "shopping_db"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                    return instance
                }
        }
    }
}