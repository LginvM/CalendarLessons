package ru.loginov.calendarlessons.DB.DI_database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.loginov.calendarlessons.DB.repository.Repository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): lessonDatabase{
        return Room.databaseBuilder(context, lessonDatabase::class.java,"lesson_db")
            .fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideRepository(database: lessonDatabase): Repository{
        return Repository(
            userDao = database.userDao(),
            typeLessonDao = database.typeDao(),
            lessonDao = database.lessonDao()
        )
    }
}