package ru.loginov.calendarlessons.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.loginov.calendarlessons.Screens.allAboutUser.InfoDetailEntry
import ru.loginov.calendarlessons.Screens.allAboutUser.UserInfo
import ru.loginov.calendarlessons.ViewModels.DetailState

enum class Routes{
    Home, Detail
}

@Composable
fun DrumNavigation(
    navHostController: NavHostController = rememberNavController()
){
    NavHost(navHostController, startDestination = Routes.Detail.name){
        composable(route = Routes.Detail.name){
            InfoDetailEntry(Modifier, state = DetailState(),{},{},{},{},{},{},{}){
            }
        }
    }
}