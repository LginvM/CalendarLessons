package ru.loginov.calendarlessons.Screens.Calendar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.loginov.calendarlessons.DB.tables.Lessons_slot
import ru.loginov.calendarlessons.ViewModels.Calendar.CalendarViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val slots by viewModel.slotsList
    val pendingSlotId by viewModel.pendingBookingSlotId
    val availableSlot by viewModel.availableSlot
    val isLoading by viewModel.isLoading
    val selectedDate by viewModel.selectedDate
    val errorMessage by viewModel.error
    val showDialog by viewModel.showSuccessDialog

    Column(modifier = Modifier.padding(16.dp)) {
        DatePicker { date ->
            viewModel.setSelectedDate(date)

            println("DEBUG: userId = ${viewModel.userId}")
        }
        if (isLoading) {
            Text("Loading...")
        }
        if (slots != null) {
            Text("Available slots on ${selectedDate}:")

            if (slots.isEmpty()) {
                Text("Empty")
            } else {
                LazyColumn {
                    items(slots) { item ->
                        SlotItem(
                            slot = item.slot,
                            isBooked = item.isBooked,
                            onBook = {
                                if (!item.isBooked) viewModel.requestBooking(item.slot.id)
                            }
                        )
                    }

                }
            }
        }
        if(pendingSlotId!=null){
            AlertDialog(
                onDismissRequest = { viewModel.cancelBooking() },
                title = { Text("Accept") },
                text = { Text("Are you sure?") },
                confirmButton = {
                    TextButton(onClick = {viewModel.confirmBooking()}) {
                        Text("Yes")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { viewModel.cancelBooking() }) {
                        Text("Cancel")
                    }
                },
            )
        }
        if(showDialog){
            AlertDialog(
                onDismissRequest = { viewModel.showSuccessDialog.value = false },
                title = { Text("Success!") },
                text = { Text("Slot is book") },
                confirmButton = {
                    TextButton(onClick = {viewModel.showSuccessDialog.value = false}) {
                        Text("Ok")
                    }
                }
            )
        }
    }
}


@Composable
fun SlotItem(slot: Lessons_slot, isBooked: Boolean, onBook:() -> Unit){
    val timeFormatter = remember { SimpleDateFormat("HH:mm", Locale.getDefault()) }

    val backgroundColor = if (isBooked) Color.LightGray else Color.White
    val textColor = if (isBooked) Color.Gray else Color.Black
    val clicableEnabled = !isBooked
    Card(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(enabled = clicableEnabled) { onBook() },

    ){
        Text(
            text = "${timeFormatter.format(slot.start_time)}-${timeFormatter.format(slot.end_time)}",
            color = textColor,
            modifier = Modifier.padding(12.dp)
        )

        if(isBooked){
            Text("Занято", fontSize = 12.sp, color = Color.DarkGray, modifier = Modifier.padding(start = 12.dp, bottom = 8.dp))
        }
    }
}

@Composable
fun DatePicker(onDateSelected:(String) -> Unit){

    val locale = rememberLocale()
    val calendar = Calendar.getInstance()
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = calendar.timeInMillis)

    val formatter = remember(locale){
        SimpleDateFormat("yyyy-MM-dd",locale)
    }



    Column {
        DatePicker (
            state = datePickerState
        )
        Button(onClick = {
            val selected = datePickerState.selectedDateMillis
            if(selected!=null){
                val formattedDate = formatter.format(Date(selected))
                println("DEBUG DATEPICKER: Выбрано :$selected, строка: $formattedDate")

                onDateSelected(formattedDate)
            }
        }) {
            Text("Выбрать дату")
        }
    }

}

@Composable
fun rememberLocale():Locale{
    val configuration by rememberUpdatedState(newValue = LocalConfiguration.current)
    return remember(configuration){
        configuration.locales.get(0) ?: Locale.getDefault()
    }
}

