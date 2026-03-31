package ru.loginov.calendarlessons.ViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.loginov.calendarlessons.DB.graph.graph
import ru.loginov.calendarlessons.DB.repository.Repository
import ru.loginov.calendarlessons.DB.tables.User

class allUsersViewModel(
    private val repository: Repository = graph.repository
): ViewModel(){
    var state by mutableStateOf(HomeState())
        private set

    init {
        getUsers()
    }

    fun getUsers(){
        viewModelScope.launch{
            repository.getAllUser.collectLatest{
                state = state.copy(
                    user = it
                )
            }
        }
    }
}

data class HomeState(
    val user: List<User> = emptyList()
)