package ru.loginov.calendarlessons.Screens.allAboutUser

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
            onNameChange = viewModel::onNameChange
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
    navigateUp: () -> Unit,
){
    var isNewEnabled by remember{
        mutableStateOf(false)
    }

    Column(
        modifier =  Modifier.padding(16.dp)
    ){
        TextField(
            value = state.user,
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
    }
}