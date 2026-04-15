package ru.loginov.calendarlessons.ViewModels.Calendar

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.loginov.calendarlessons.DB.repository.Repository
import ru.loginov.calendarlessons.DB.tables.Lessons_slot
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor (
    private val repository: Repository,
    private val savedStateHandle: SavedStateHandle
): ViewModel(){

    val userId: Int = savedStateHandle.get<Int>("id") ?: -1
    val selectedDate = mutableStateOf<String?>(null)
    val availableSlot = mutableStateOf<List<Lessons_slot>>(emptyList())
    val isLoading = mutableStateOf(false)
    val error = mutableStateOf<String?>(null)


    fun setSelectedDate(date: String){
        selectedDate.value = date
        loadAvailableSlots(date)
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

    fun bookLesson(lessonsSlotId : Int){
        val date = selectedDate.value ?:return
        val currentUserId = userId ?:return

        viewModelScope.launch {
            try {
                repository.bookUser(currentUserId,lessonsSlotId,date)
                loadAvailableSlots(date)
            } catch (
            e: Exception
            ){
                error.value = "Failed to book lesson: ${e.message}"
            }
        }
    }
}
