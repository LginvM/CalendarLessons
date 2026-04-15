package ru.loginov.calendarlessons.DB.repository

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.Flow
import ru.loginov.calendarlessons.DB.DAO.Lesson
import ru.loginov.calendarlessons.DB.DAO.LessonDao
import ru.loginov.calendarlessons.DB.DAO.Lessons_slotDao
import ru.loginov.calendarlessons.DB.DAO.PhoneAndPassword
import ru.loginov.calendarlessons.DB.DAO.TypeLessonDao
import ru.loginov.calendarlessons.DB.DAO.UserDao
import ru.loginov.calendarlessons.DB.tables.Lessons
import ru.loginov.calendarlessons.DB.tables.Lessons_slot
import ru.loginov.calendarlessons.DB.tables.User
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

class Repository(
    private val userDao: UserDao,
    private val typeLessonDao: TypeLessonDao,
    private val lessonDao: LessonDao,
    private val Lessons_slotDao: Lessons_slotDao
){
    val getAllUser = userDao.getAllUsers()

    suspend fun getPhoneAndPassword(phone: String): PhoneAndPassword?
        = userDao.getNumberAndPassword(phone)


    fun getUser(id:Int) = userDao.getUser(id)


    suspend fun insertUser(user: User){
        userDao.insert(user)
    }

    suspend fun updateUser(user: User){
        userDao.update(user)
    }

    suspend fun deleteUser(user: User){
        userDao.delete(user)
    }


    //манипуляции с календарём

    //Фильтрует слоты которые не заняты в определенный день недели
    suspend fun getAvailableSlots(date:String):List<Lessons_slot>{
        val allSlots = lessonDao.getAllSlots()
        val bookedSlotId = lessonDao.getBookedSlotsId(date)

        val dayOfWeek = LocalDate.parse(date.trim()).dayOfWeek.value % 7

        return allSlots
            .filter{it.day_of_week == dayOfWeek}
            .filterNot { bookedSlotId.contains(it.id) }
    }

    //Создание записи о занятии
    suspend fun bookUser(userId:Int, lessonSlotId:Int, date: String){
        val lesson = Lesson(
            userId = userId,
            lessonSlotId = lessonSlotId,
            date = date
            )

        //lessonDao.bookLesson(Lesson)
    }

    suspend fun initializeDefaultSlots(){
        val existingsSlots = lessonDao.getAllSlots()
        if(existingsSlots.isNotEmpty()) return

        val defaultSlots = mutableListOf<Lessons_slot>()
        val dayOfWeek = 1..7
        val startHour = 10
        val endHour = 18

        for (day in dayOfWeek){
            for(hour in startHour until endHour){
                val startCalendar = Calendar.getInstance().apply{
                    clear()
                    set(Calendar.HOUR_OF_DAY,hour +1)
                    set(Calendar.MINUTE,0)
                    set(Calendar.SECOND,0)
                    set(Calendar.MILLISECOND,0)
                }
                val startTime = java.sql.Time(startCalendar.timeInMillis)

                val endCalendar = Calendar.getInstance().apply {
                    clear()
                    set(Calendar.HOUR_OF_DAY,hour +1)
                    set(Calendar.MINUTE,0)
                    set(Calendar.SECOND,0)
                    set(Calendar.MILLISECOND,0)
                }

                val endTime = java.sql.Time(endCalendar.timeInMillis)

                defaultSlots.add(
                    Lessons_slot(
                        id = 0,
                        start_time = startTime,
                        end_time = endTime,
                        day_of_week = day
                    )
                )
            }
        }
        Lessons_slotDao.insertAll(defaultSlots)
    }
}