package ru.loginov.calendarlessons.ViewModels.userPages

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.loginov.calendarlessons.DB.graph.graph
import ru.loginov.calendarlessons.DB.repository.Repository
import ru.loginov.calendarlessons.DB.tables.User

class DetailViewModel constructor(
    private val userId:Int,
    private val repository: Repository = graph.repository
): ViewModel(){
    var state by mutableStateOf(DetailState())
        private set

    init{
        getUser()
        if (userId!=-1){
            viewModelScope.launch {
                repository.getUser(userId)
                    .collectLatest {
                        state = state.copy(
                            name = it.name,
                            lastname = it.lastname,
                            number = it.number,
                            password = it.password,
                            birthday = it.birthday,
                            balance = it.balance,
                            notice = it.notice
                        )
                    }
            }
        }
    }

    fun onNameChange(newValue: String){
        state = state.copy(name = newValue)
    }

    fun onLastNameChange(newValue: String){
        state = state.copy(lastname = newValue)
    }

    fun onNumber(newValue: String){
        state = state.copy(number = newValue)
    }

    fun onPassword(newValue: String){
        state = state.copy(password = newValue)
    }

    fun onBirthday(newValue: String){
        state = state.copy(birthday = newValue)
    }

    fun onBalance(newValue: Int){
        state = state.copy(balance = newValue)
    }

    fun onNotice(newValue: String){
        state = state.copy(notice = newValue)
    }

    fun addUser(){
        viewModelScope.launch {
            repository.insertUser(
                User(
                    name = state.name,
                    lastname = state.lastname,
                    number = state.number,
                    password = state.password,
                    birthday = state.birthday,
                    balance = state.balance,
                    notice = state.notice
                )
            )
        }
    }

    fun updateUser(id:Int){
        viewModelScope.launch {
            repository.insertUser(
                User(
                    name = state.name,
                    lastname = state.lastname,
                    number = state.number,
                    password = state.password,
                    birthday = state.birthday,
                    balance = state.balance,
                    notice = state.notice
                )
            )
        }
    }

    fun getUser(){
        viewModelScope.launch {
            repository.getAllUser.collectLatest {
                state = state.copy(it)
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactor(private val id:Int): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(userId = id) as T
    }
}

data class DetailState(
    val user:List<User> = emptyList(),
    val name:String = "",
    val lastname:String = "",
    val number: String = "",
    val password:String = "",
    val birthday:String = "",
    val balance:Int = 0,
    val notice:String = "",
    val isUpdatingUser: Boolean = false,
)