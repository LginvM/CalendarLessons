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
import ru.loginov.calendarlessons.DB.graph.GraphProvide
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
    NavHost(navHostController, startDestination = Routes.Auth.name){

        composable(route = Routes.Auth.name){
            auth(navHostController)

//            userId ->
//            navHostController.navigate(Routes.Calendar.name)
//            navHostController.previousBackStackEntry.savedStateHandle?.set("userId",userId)
        }

        composable(Routes.Calendar.name){
            val userId = navHostController.currentBackStackEntry?.savedStateHandle
                ?.get<Int>("userId") ?: return@composable
            val viewModel: CalendarViewModel = viewModel(
                factory = object : ViewModelProvider.Factory{
                    @Composable
                    @Suppress("UNCHECKED_CAST")
                    override fun <T: ViewModel?> create(modelClass:Class<T>): T {
                        val repository = (LocalContext.current.applicationContext as GraphProvide)
                        return CalendarViewModel(repository) as T
                    }
                }
            )
        }
        CalendarScreen(navHostController, CalendarViewModel )

        composable(route = Routes.ListUsers.name){
            screenOfUsers({
                id -> navHostController.navigate(route = "${Routes.UpdateUser.name}?id=$id")
            })
        }
        composable(
            route = "${Routes.UpdateUser.name}?id={id}",
            arguments = listOf(navArgument("id"){type = NavType.IntType})
        ){
            val id = it.arguments?.getInt("id") ?: -1
            UserInfo(
                id = id
            ){
                navHostController.navigateUp()
            }
        }
    }
}
