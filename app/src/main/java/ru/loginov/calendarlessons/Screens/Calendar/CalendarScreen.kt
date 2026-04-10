package ru.loginov.calendarlessons.Screens.Calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ru.loginov.calendarlessons.ViewModels.Calendar.CalendarViewModel
import ru.loginov.calendarlessons.ViewModels.authPage.authViewModel

@Composable
fun CalendarScreen(
    navController: NavController,
    viewModel: CalendarViewModel = viewModel()
){
    val avaibaleSlots by viewModel.availableSlot.observeAsState(emptyList())
}