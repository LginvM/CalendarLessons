package ru.loginov.calendarlessons.ViewModels.Calendar

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.loginov.calendarlessons.DB.graph.graph
import ru.loginov.calendarlessons.DB.repository.Repository
import ru.loginov.calendarlessons.DB.tables.Lessons_slot
import java.util.Date

class CalendarViewModel (
    private val repository: Repository = graph.repository
): ViewModel(){
    val selectedDate = mutableStateOf<String?>(null)
    val availableSlot = mutableStateOf<List<Lessons_slot>>(emptyList())
    val userId = mutableStateOf<Int?>(null)
    val isLoading = mutableStateOf(false)
    val error = mutableStateOf<String?>(null)

    fun setSelectedDate(date: String){
        selectedDate.value = date
        //loadAvailableSlots(date)
    }

    private fun loadAvailableSlots(date:String){
        isLoading.value = true
        viewModelScope.launch {
            try {
                val slots = repository.getAvailableSlots(date)
                availableSlot.value = slots
            } catch (e: Exception) {
                error.value = "Error downloading slots"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun bookSlot(lessonsSlotId : Int){
        val date = selectedDate.value ?:return
        val currentUserId = userId.value ?:return

        viewModelScope.launch {
            repository.bookUser(currentUserId,lessonsSlotId,date)
            loadAvailableSlots(date)
        }
    }
}