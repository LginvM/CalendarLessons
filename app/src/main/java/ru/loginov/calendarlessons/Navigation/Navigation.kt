package ru.loginov.calendarlessons.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.loginov.calendarlessons.DB.graph.AppApplication
import ru.loginov.calendarlessons.Screens.Calendar.CalendarScreen
import ru.loginov.calendarlessons.Screens.allAboutUser.UserInfo
import ru.loginov.calendarlessons.Screens.allAboutUser.screenOfUsers
import ru.loginov.calendarlessons.Screens.auth.auth
import ru.loginov.calendarlessons.ViewModels.Calendar.CalendarViewModel

enum class Routes{
    Auth, Calendar, ListUsers, UpdateUser
}

@Composable
fun DrumNavigation(
    navHostController: NavHostController = rememberNavController()
){
    NavHost(navHostController, startDestination = Routes.ListUsers.name){

        composable(route = Routes.ListUsers.name) {
            screenOfUsers(
                onNavigate = { id ->
                    navHostController.navigate("${Routes.UpdateUser.name}/$id")
                }
            )
        }

        composable(route = "${Routes.UpdateUser.name}/{id}",
            arguments = listOf(
                navArgument("id"){
                    type = NavType.IntType
                    defaultValue = -1
                }
            )) { UserInfo(navHostController::navigateUp)

        }

    }
}
