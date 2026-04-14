package ru.loginov.calendarlessons.Screens.Calendar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.loginov.calendarlessons.DB.tables.Lessons_slot
import ru.loginov.calendarlessons.ViewModels.Calendar.CalendarViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel = hiltViewModel()
){
    val availableSlot by viewModel.availableSlot
    val isLoading by viewModel.isLoading
    val selectedDate by viewModel.selectedDate
    val errorMessage by viewModel.error

    Column(modifier = Modifier.padding(16.dp)){
        DatePicker{ date ->
            viewModel.setSelectedDate(date)
        }
        if(isLoading){
            Text("Loading...")
        }
        if(selectedDate != null){
            Text("Available slots on ${selectedDate}:")
        if(availableSlot.isEmpty()){
            Text("Empty")
        }else{
            LazyColumn {
                items(availableSlot){
                    slot -> SlotItem(slot = slot){
                        viewModel.bookLesson(slot.id)
                }
                }
            }
        }
        }
    }
}

@Composable
fun SlotItem(slot: Lessons_slot,onBook:() -> Unit){
    Card(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable{onBook()},

    ){
        Text(
            text = "${slot.start_time}-${slot.end_time}",
            modifier = Modifier.padding(12.dp)
        )
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
                onDateSelected(formatter.format(Date(selected)))
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

