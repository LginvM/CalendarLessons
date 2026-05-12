package ru.loginov.calendarlessons.ViewModels.authPage


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.loginov.calendarlessons.DB.repository.Repository
import javax.inject.Inject


@HiltViewModel
class authViewModel @Inject constructor(
    private val repository: Repository
): ViewModel(){

    private val _uiState = MutableStateFlow(AuthState())
    val uiState : StateFlow<AuthState> = _uiState


    fun onNumberChanged(newNumber: String) {
        _uiState.update {
            it.copy(number = newNumber)
        }
    }

    fun onPasswordChanged(newPassword: String) {
        _uiState.update {
            it.copy(password = newPassword)
        }
    }

    fun login() {
        viewModelScope.launch {
            _uiState.update{ it.copy(isLoading = true, error = null)}

            // Снимаем данные из state в локальные переменные, чтобы безопасно использовать их в фоне
            val number = _uiState.value.number.trim()
            val passwordInput = _uiState.value.password.trim()


            val user = repository.getPhoneAndPassword(number)

            if (user == null) {
                // Пользователь не найден
                _uiState.update { it.copy(isLoading = false, error = "Пользователь не найден")  }

                return@launch
            }

            if(user != null){
                val ok = passwordInput == user.password
                if(ok){
                    repository.initializeDefaultSlots()
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isAuthenticated = ok,
                            userId = user.id
                        ) }
                } else {
                    _uiState.update{
                        it.copy(
                            isLoading = false,
                            error = if (ok) null else "Неверный пароль"
                        )}
                }
            }


        }
    }
}



data class AuthState(
    val number: String = "",
    val password: String = "",
    val name:String = "",
    val userId: Int? = null,
    val isAuthenticated: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)
