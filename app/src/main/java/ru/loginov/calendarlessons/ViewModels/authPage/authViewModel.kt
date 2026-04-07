package ru.loginov.calendarlessons.ViewModels.authPage

import ru.loginov.calendarlessons.DB.DAO.UserDao
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.loginov.calendarlessons.DB.DAO.PhoneAndPassword
import ru.loginov.calendarlessons.DB.graph.graph
import ru.loginov.calendarlessons.DB.repository.Repository
import ru.loginov.calendarlessons.DB.tables.User
import ru.loginov.calendarlessons.ViewModels.userPages.DetailViewModel
import ru.loginov.calendarlessons.ViewModels.userPages.HomeState

class authViewModel(
    private val phone:String,
    private val pas:String,
    private val repository: Repository = graph.repository
): ViewModel(){

    var number by mutableStateOf("")

    var password by mutableStateOf("")
    private val queryFlow = MutableStateFlow<Pair<String, String>?>(null)

    var state by mutableStateOf(AuthState())
        private set

    val matchedData: StateFlow<PhoneAndPassword?> = queryFlow.filterNotNull()
        .map{(number,password)-> Pair(number.trim(),password.toString())}
        .flatMapLatest { (number,password)-> repository.getPhoneAndPassword(number,password) }
        .stateIn(viewModelScope, SharingStarted.Lazily,null)

    init {

    }

    fun check(){
        val n = number.trim()
        val p = password.trim()
        if (n.isEmpty() || p.isEmpty()) return
        queryFlow.value = Pair(n, p) // это запустит matchedData flow
    }

}

@Suppress("UNCHECKED_CAST")
class authViewModelFactor(private val phone: String, private val pas:String): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return authViewModel(phone, pas) as T
    }
}

data class AuthState(
    val number: String = "",
    val password: String = ""
)