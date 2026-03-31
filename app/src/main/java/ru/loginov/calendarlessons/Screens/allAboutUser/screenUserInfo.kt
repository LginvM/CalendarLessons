package ru.loginov.calendarlessons.Screens.allAboutUser

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.loginov.calendarlessons.ViewModels.DetailState
import ru.loginov.calendarlessons.ViewModels.DetailViewModel
import ru.loginov.calendarlessons.ViewModels.DetailViewModelFactor
import ru.loginov.calendarlessons.ui.theme.Shapes

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UserInfo(
    id:Int,
    navigateUp:() -> Unit
){
    val viewModel = viewModel<DetailViewModel>(factory = DetailViewModelFactor(id))
    Scaffold {

        InfoDetailEntry(
            state = viewModel.state,
            onNameChange = viewModel::onNameChange,
            onLastNameChange = viewModel::onLastNameChange,
            onNumber = viewModel::onNumber,
            onPassword = viewModel::onPassword,
            onBirthday = viewModel::onBirthday,
            onBalance = viewModel::onBalance,
            onNotice = viewModel::onNotice,
        ){
            navigateUp.invoke()
        }
    }
}

@Composable
private fun InfoDetailEntry(
    modifier: Modifier = Modifier,
    state: DetailState,
    onNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onNumber: (String) -> Unit,
    onPassword: (String) -> Unit,
    onBirthday: (String) -> Unit,
    onBalance: (Int) -> Unit,
    onNotice: (String) -> Unit,
    navigateUp: () -> Unit,

){
    var textStr by rememberSaveable { mutableStateOf("") }
    
    var isFocused by remember{
        mutableStateOf(false)
    }

    Column(
        modifier =  Modifier.padding(16.dp)
    ){
        TextField(
            value = state.name,
            onValueChange = { onNameChange(it) },
            label = {
                Text(text = "Имя")
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.LightGray
            ),
            shape = Shapes.large
        )
        Spacer(modifier= Modifier.Companion.size(12.dp))

        TextField(
            value = state.lastname,
            onValueChange = { onLastNameChange(it) },
            label = {
                Text(text = "Фамилия")
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.LightGray
            ),
            shape = Shapes.large
        )
        Spacer(modifier= Modifier.Companion.size(12.dp))

        TextField(
            value = state.number,
            onValueChange = { onNumber(it) },
            label = {
                Text(text = "Номер")
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.LightGray
            ),
            shape = Shapes.large
        )
        Spacer(modifier= Modifier.Companion.size(12.dp))

        TextField(
            value = state.password,
            onValueChange = { onPassword(it) },
            label = {
                Text(text = "Пароль")
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.LightGray
            ),
            shape = Shapes.large
        )
        Spacer(modifier= Modifier.Companion.size(12.dp))

        TextField(
            value = state.birthday,
            onValueChange = { onBirthday(it) },
            label = {
                Text(text = "Номер")
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.LightGray
            ),
            shape = Shapes.large
        )
        Spacer(modifier= Modifier.Companion.size(12.dp))


        LaunchedEffect(key1 = state.balance,isFocused){
            if(!isFocused){
                textStr = state.balance.let { String.format("%d") }
            }
        }


        TextField(
            value = textStr,
            onValueChange = { newText -> textStr.filter { it.isDigit() } },
            label = {
                Text(text = "Баланс")
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.LightGray
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = Shapes.large
        )
        Spacer(modifier= Modifier.Companion.size(12.dp))

        TextField(
            value = state.notice,
            onValueChange = { onNotice(it) },
            label = {
                Text(text = "Заметки")
            },
            modifier = Modifier.fillMaxWidth().height(260.dp),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.LightGray
            ),
            shape = Shapes.large
        )
        Spacer(modifier= Modifier.Companion.size(12.dp))

    }
}



fun intConv(input:String):Int{
    val normalized = input.filter { it.isDigit() }
    return normalized.toInt()
}

@Composable
@Preview(showSystemUi = true)
fun PrevDetailEntry(){
    InfoDetailEntry(
        state = DetailState(),
        onNameChange = { },
        onLastNameChange = { },
        onNumber = { },
        onPassword = { },
        onBirthday = { },
        navigateUp = {},
        onBalance = { },
        onNotice = { },
    )
}