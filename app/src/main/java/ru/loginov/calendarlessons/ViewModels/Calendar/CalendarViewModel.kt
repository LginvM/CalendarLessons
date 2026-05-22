package ru.loginov.calendarlessons.ViewModels.Calendar

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.loginov.calendarlessons.DB.repository.Repository
import ru.loginov.calendarlessons.models.SlotUiModel
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor (
    private val repository: Repository,
    private val savedStateHandle: SavedStateHandle
): ViewModel(){

    val userId: Int = savedStateHandle.get<Int>("id") ?: -1

    //New
    private val _selectedDate = MutableStateFlow<String?>(null)
    val selectedDate: StateFlow<String?> = _selectedDate.asStateFlow()


    private val _slotsList = MutableStateFlow<List<SlotUiModel>>(emptyList())
    val slotsList : StateFlow<List<SlotUiModel>> = _slotsList.asStateFlow()


    private val _isLoading = MutableStateFlow(false)
    val isLoading : StateFlow<Boolean> = _isLoading.asStateFlow()


    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()


    private val _pendingBookingSlotId = MutableStateFlow<Int?>(null)
    val pendingBookingSlotId: StateFlow<Int?> = _pendingBookingSlotId.asStateFlow()


    private val _showSuccessDialog = MutableStateFlow(false)
    val showSuccessDialog : StateFlow<Boolean> = _showSuccessDialog.asStateFlow()






    fun setSelectedDate(date: String){
        _selectedDate.value = date
        _pendingBookingSlotId.value = null
        loadAvailableSlots(date)
    }

    fun requestBooking(slotId: Int){
        _pendingBookingSlotId.value = slotId
    }

    fun confirmBooking(){
        val slotId = _pendingBookingSlotId.value ?:return
        val date = _selectedDate.value ?: return
        if(userId == -1) return

        viewModelScope.launch {
            try {
                repository.bookUser(userId,slotId,date)
                _showSuccessDialog.value = true
                _pendingBookingSlotId.value = null
                loadAvailableSlots(date)
            } catch (e: Exception){
                _error.value = "Ошибка бронирования: ${e.message}"
                _pendingBookingSlotId.value = null
            }
        }
    }

    fun dismissSuccessDialog(){
        _showSuccessDialog.value = false
    }

    fun cancelBooking(){
        _pendingBookingSlotId.value = null
    }

    private fun loadAvailableSlots(date:String){
        _isLoading.value = true
        viewModelScope.launch {

            try {
                val slots = repository.getAllSlotsForDate(date)
                _slotsList.value = slots
            } catch (e: Exception) {
                _error.value = "Error downloading slots"
            } finally {
                _isLoading.value = false
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
                _error.value = "Failed to book lesson: ${e.message}"
            }
        }
    }
}
