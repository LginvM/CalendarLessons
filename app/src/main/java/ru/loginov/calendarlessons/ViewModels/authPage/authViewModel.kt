package ru.loginov.calendarlessons.ViewModels.authPage


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.loginov.calendarlessons.DB.graph.graph
import ru.loginov.calendarlessons.DB.repository.Repository



class authViewModel(
    private val repository: Repository = graph.repository
): ViewModel(){

    val uiState = mutableStateOf(AuthState())


    fun onNumberChanged(newNumber: String) {
        uiState.value = uiState.value.copy(number = newNumber)
    }

    fun onPasswordChanged(newPassword: String) {
        uiState.value = uiState.value.copy(password = newPassword)
    }

    fun login() {
        viewModelScope.launch {
            uiState.value = uiState.value.copy(isLoading = true, error = null)

            // Снимаем данные из state в локальные переменные, чтобы безопасно использовать их в фоне
            val number = uiState.value.number.trim()
            val passwordInput = uiState.value.password.trim()


            val user = withContext(Dispatchers.IO) {
                repository.getPhoneAndPassword(
                    uiState.value.number.trim()
                )
            }
            if (user == null) {
                // Пользователь не найден
                uiState.value =
                    uiState.value.copy(isLoading = false, error = "Пользователь не найден")
                return@launch
            }

            val ok = passwordInput == user.password
            If(ok){
                uiState.value = uiState.value.copy(
                isLoading = false,
                isAuthenticated = ok,
                userId = user.id
                )
            } else {
            uiState.value = uiState.value.copy(
                isLoading = false,
                error = if (ok) null else "Неверный пароль"
                )
        }
    }
}



data class AuthState(
    val number: String = "",
    val password: String = "",
    val name:String = "",
    val isAuthenticated: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)
