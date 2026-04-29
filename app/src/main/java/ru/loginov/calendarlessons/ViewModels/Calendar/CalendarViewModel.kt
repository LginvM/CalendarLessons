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

    val pendingBookingSlotId = mutableStateOf<Int?>(null)

    val showSuccessDialog = mutableStateOf(false)

    fun setSelectedDate(date: String){
        selectedDate.value = date
        pendingBookingSlotId.value = null
        loadAvailableSlots(date)
    }

    fun requestBooking(slotId: Int){
        pendingBookingSlotId.value = slotId
    }

    fun confirmBooking(){
        val slotId = pendingBookingSlotId.value ?:return
        val date = selectedDate.value ?: return
        if(userId == -1) return

        viewModelScope.launch {
            try {
                repository.bookUser(userId,slotId,date)
                showSuccessDialog.value = true
                pendingBookingSlotId.value = null
                loadAvailableSlots(date)
            } catch (e: Exception){
                error.value = "Ошибка бронирования: ${e.message}"
                pendingBookingSlotId.value = null
            }
        }
    }

    fun cancelBooking(){
        pendingBookingSlotId.value = null
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
