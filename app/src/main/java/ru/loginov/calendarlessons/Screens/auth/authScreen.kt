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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ru.loginov.calendarlessons.Navigation.Routes
import ru.loginov.calendarlessons.ViewModels.authPage.authViewModel
import ru.loginov.calendarlessons.ViewModels.userPages.allUsersViewModel


@Composable
fun auth(
    navController: NavController
){
    val authViewModel = viewModel(modelClass = authViewModel::class.java)
    val uiState = authViewModel.uiState


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center // Центрирует содержимое
    ) {
        Column(

        ) {


            TextField(
                value = uiState.value.number,
                onValueChange = { authViewModel.onNumberChanged(it) },
                label = {
                    Text("Номер")
                },
                modifier = Modifier
            )
            Spacer(Modifier.size(8.dp))
            TextField(value = uiState.value.password,
                onValueChange = { authViewModel.onPasswordChanged(it) },
                label = {
                    Text("Пароль")
                },
                trailingIcon = {
                    // Кнопка-иконка справа
                    IconButton(onClick = { authViewModel.login() }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Очистить"
                        )
                    }
                }
            )
            uiState.value.error?.let { Text(text = it, color = Color.Red) }
            if (uiState.value.isAuthenticated) {
                Text(text = "Успешно", color = Color.Green)
                LaunchedEffect(Unit) {
                    navController.navigate("${Routes.First.name}?id=${uiState.value.userId}")
                }
            }



        }
    }
}


@Composable
fun uiAuth(){

}

@Composable
@Preview(showSystemUi = true)
fun prew(){
}
