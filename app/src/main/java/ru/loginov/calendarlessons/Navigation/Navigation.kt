package ru.loginov.calendarlessons.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.loginov.calendarlessons.Screens.allAboutUser.UserInfo
import ru.loginov.calendarlessons.Screens.allAboutUser.screenOfUsers
import ru.loginov.calendarlessons.Screens.auth.auth

enum class Routes{
    Auth, First, Second
}

@Composable
fun DrumNavigation(
    navHostController: NavHostController = rememberNavController()
){
    NavHost(navHostController, startDestination = Routes.Auth.name){

        composable(route = Routes.Auth.name){
            auth(navController = navHostController)
        }

        composable(route = Routes.First.name){
            screenOfUsers({
                id -> navHostController.navigate(route = "${Routes.Second.name}?id=$id")
            })
        }
        composable(
            route = "${Routes.Second.name}?id={id}",
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
