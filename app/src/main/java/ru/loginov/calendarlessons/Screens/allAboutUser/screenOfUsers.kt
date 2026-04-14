package ru.loginov.calendarlessons.Screens.allAboutUser

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.loginov.calendarlessons.DB.tables.User
import ru.loginov.calendarlessons.ViewModels.userPages.allUsersViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun screenOfUsers(
    onNavigate:(Int) -> Unit,

){
    val viewModel:allUsersViewModel = hiltViewModel()
    val userState by viewModel.state.collectAsState()

    Scaffold(
        floatingActionButton={
            FloatingActionButton(onClick = {onNavigate.invoke(-1)}) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },

    ){ paddingValues ->
        if(userState.user.isEmpty()){
            Box(Modifier
                .fillMaxSize()
                .padding(paddingValues))
            Spacer(Modifier.padding(20.dp))
            Text("Нет пользователей")
        } else
            LazyColumn(Modifier
                .fillMaxSize()
                .padding(paddingValues),
                contentPadding = PaddingValues(vertical = 8.dp, horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)) {

                items(items = userState.user, {it.id}) {
                    user -> UserCard(
                        user = user
                    ){

                        onNavigate.invoke(user.id)

                    }
                }
        }

    }

}

@Composable
fun UserCard(
    user: User,
    onUserClick:() -> Unit
){
    Card(
        Modifier
            .fillMaxWidth()
            .clickable {
                onUserClick.invoke()
            }
            .padding(8.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ){
            Column(Modifier.padding(8.dp)){
                Text(text = user.name + " " + user.lastname, fontWeight = FontWeight.Bold)
                Spacer(Modifier.padding(8.dp))
                Text(text = user.number)
            }
        }
    }
}

//@Composable
//@Preview(showSystemUi = true)
//fun screenOfUsers(){
//
//}