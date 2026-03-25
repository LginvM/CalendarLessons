package ru.loginov.calendarlessons.ViewModels

import androidx.lifecycle.ViewModel
import ru.loginov.calendarlessons.DB.graph.graph
import ru.loginov.calendarlessons.DB.repository.Repository
import ru.loginov.calendarlessons.DB.tables.User

class DetailViewModel constructor(
    private val userId:Int,
    private val repository: Repository = graph.repository
): ViewModel(){

}

data class DetailState(
    val user:List<User> = emptyList()
)