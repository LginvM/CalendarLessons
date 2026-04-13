package ru.loginov.calendarlessons.ViewModels.userPages

import androidx.lifecycle.SavedStateHandle
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
class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: Repository
): ViewModel(){
    private val userId:Int = savedStateHandle.get<Int>("userId") ?: -1
    private val _state = MutableStateFlow(DetailState())
    val state : StateFlow<DetailState> = _state

    init{
        getUser()
        if (userId!=-1){
            viewModelScope.launch {
                repository.getUser(userId)
                    .collectLatest {
                        _state.value = state.value.copy(
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
        _state.update{it.copy(name = newValue)}
    }

    fun onLastNameChange(newValue: String){
        _state.update{it.copy(lastname = newValue)}
    }

    fun onNumber(newValue: String){
        _state.update{it.copy(number = newValue)}
    }

    fun onPassword(newValue: String){
        _state.update{it.copy(password = newValue)}
    }

    fun onBirthday(newValue: String){
        _state.update{it.copy(birthday = newValue)}
    }

    fun onBalance(newValue: Int){
        _state.update{it.copy(balance = newValue)}
    }

    fun onNotice(newValue: String){
        _state.update{it.copy(notice = newValue)}
    }

    fun addUser(){
        viewModelScope.launch {
            val currentUser = _state.value
            repository.insertUser(
                User(
                    name = currentUser.name,
                    lastname = currentUser.lastname,
                    number = currentUser.number,
                    password = currentUser.password,
                    birthday = currentUser.birthday,
                    balance = currentUser.balance,
                    notice = currentUser.notice
                )
            )
        }
    }

    fun updateUser(){
        viewModelScope.launch {
            val currentUser = _state.value
            repository.updateUser(
                User(
                    name = currentUser.name,
                    lastname = currentUser.lastname,
                    number = currentUser.number,
                    password = currentUser.password,
                    birthday = currentUser.birthday,
                    balance = currentUser.balance,
                    notice = currentUser.notice
                )
            )
        }
    }

    fun getUser(){
        viewModelScope.launch {
            repository.getAllUser.collectLatest {
                _state.update { it.copy(
                    user = state.value.user
                ) }
            }
        }
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