package ru.loginov.calendarlessons.Screens.auth

import android.R.attr.text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun auth(){

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
                    IconButton(onClick = {  }) {
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