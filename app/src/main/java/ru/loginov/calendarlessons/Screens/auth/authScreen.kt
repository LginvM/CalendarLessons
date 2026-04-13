package ru.loginov.calendarlessons.Screens.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import dagger.hilt.android.AndroidEntryPoint
import ru.loginov.calendarlessons.Navigation.Routes
import ru.loginov.calendarlessons.ViewModels.authPage.authViewModel


@Composable

fun auth(
    navController: NavController,
){
    val viewModel: authViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center // Центрирует содержимое
    ) {
        Column(

        ) {


            TextField(
                value = uiState.number,
                onValueChange = { viewModel.onNumberChanged(it) },
                label = {
                    Text("Номер")
                },
                modifier = Modifier
            )
            Spacer(Modifier.size(8.dp))
            TextField(value = uiState.password,
                onValueChange = { viewModel.onPasswordChanged(it) },
                label = {
                    Text("Пароль")
                },
                trailingIcon = {
                    // Кнопка-иконка справа
                    IconButton(onClick = { viewModel.login() }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Очистить"
                        )
                    }
                }
            )
            uiState.error?.let { Text(text = it, color = Color.Red) }
            if (uiState.isAuthenticated) {
                Text(text = "Успешно", color = Color.Green)
                LaunchedEffect(Unit) {
                    navController.previousBackStackEntry?.savedStateHandle?.set("userId",uiState.userId)
                    navController.navigate("${Routes.Calendar.name}?id=${uiState.userId}")
                }
            }



        }
    }
}


@Composable
@Preview(showSystemUi = true)
fun prew(){
}
