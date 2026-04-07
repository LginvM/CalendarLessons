package ru.loginov.calendarlessons.Screens.auth

import android.R.attr.text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlexDirection.Companion.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.loginov.calendarlessons.DB.graph.graph
import ru.loginov.calendarlessons.DB.repository.Repository
import ru.loginov.calendarlessons.ViewModels.authPage.authViewModel
import ru.loginov.calendarlessons.ViewModels.authPage.authViewModelFactor
import ru.loginov.calendarlessons.ViewModels.userPages.DetailViewModel
import ru.loginov.calendarlessons.ViewModels.userPages.DetailViewModelFactor
import ru.loginov.calendarlessons.ViewModels.userPages.HomeState


@Composable
fun auth(

){

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center // Центрирует содержимое
    ) {
        Column(

        ) {


            TextField(
                value = "",
                onValueChange = {},
                label = {
                    Text("Номер")
                },
                modifier = Modifier
            )
            Spacer(Modifier.size(8.dp))
            TextField(value = "",
                onValueChange = {},
                label = {
                    Text("Пароль")
                },
                trailingIcon = {
                    // Кнопка-иконка справа
                    IconButton(onClick = { if ()  }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Очистить"
                        )
                    }
                }
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun prew(){
    auth()
}