package ru.loginov.calendarlessons.DB.graph

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.loginov.calendarlessons.DB.repository.Repository
import javax.inject.Inject

@HiltAndroidApp
class AppApplication: Application() {

    @Inject
    lateinit var repository: Repository

    override fun onCreate() {
        super.onCreate()

        CoroutineScope(Dispatchers.IO).launch {
            repository.initializeTestUser()
        }
    }
}

