package ru.loginov.calendarlessons.models

import ru.loginov.calendarlessons.DB.tables.Lessons_slot

data class SlotUiModel (
    val slot: Lessons_slot,
    val isBooked: Boolean
)