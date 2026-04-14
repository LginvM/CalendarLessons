package ru.loginov.calendarlessons.ViewModels.userPages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.loginov.calendarlessons.DB.repository.Repository
import ru.loginov.calendarlessons.DB.tables.User
import javax.inject.Inject

@HiltViewModel
class allUsersViewModel @Inject constructor(
    private val repository: Repository
): ViewModel(){

    private val _state = MutableStateFlow(HomeState())
    val state : StateFlow<HomeState> = _state

    init {
        getUsers()
    }

    fun getUsers(){
        viewModelScope.launch{
            repository.getAllUser.collectLatest{ userFromDB ->
                _state.update { currentState ->
                    currentState.copy(user = userFromDB)
                 }
            }
        }
    }
}

data class HomeState(
    val user: List<User> = emptyList()
)