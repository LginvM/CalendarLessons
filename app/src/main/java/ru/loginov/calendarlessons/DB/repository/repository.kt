package ru.loginov.calendarlessons.DB.repository

import kotlinx.coroutines.flow.first
import ru.loginov.calendarlessons.DB.DAO.LessonDao
import ru.loginov.calendarlessons.DB.DAO.Lessons_slotDao
import ru.loginov.calendarlessons.DB.DAO.PhoneAndPassword
import ru.loginov.calendarlessons.DB.DAO.TypeLessonDao
import ru.loginov.calendarlessons.DB.DAO.UserDao
import ru.loginov.calendarlessons.DB.tables.Lessons
import ru.loginov.calendarlessons.DB.tables.Lessons_slot
import ru.loginov.calendarlessons.DB.tables.User
import ru.loginov.calendarlessons.models.SlotUiModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

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
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US).apply{
            timeZone = TimeZone.getTimeZone("UTC")
        }



        val dateObj = formatter.parse(date) ?: return

        println(">>> REPO DEBUG: Строка входная: $date")
        println(">>> REPO DEBUG: millis после парсинга: ${dateObj.time}")
        println(">>> REPO DEBUG: Дата в UTC: ${dateObj}")

        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply{
            time = dateObj
            set(Calendar.HOUR_OF_DAY, 10)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val finalTimeMillis = calendar.timeInMillis

        println("+++ FIX DEBUG: Было (00:00): ${dateObj.time}")
        println("+++ FIX DEBUG: Стало (10:00): $finalTimeMillis -> ${Date(finalTimeMillis)}")

        val entityForDB = Lessons(
            id = 0,
            user_id = userId,
            lesson_slot_id = lessonSlotId,
            lesson_date = Date(finalTimeMillis),
            created_at = Date()
            )

        println(">>> REPO DEBUG: Сохраняем дату. Строка =$date, millis =${dateObj.time}, Result = ${entityForDB.lesson_date} ")

        lessonDao.bookLesson(entityForDB)
    }

    suspend fun initializeDefaultSlots(){
        val existingsSlots = lessonDao.getAllSlots()
        if(existingsSlots.isNotEmpty()) {
            println("DEBUG: Слоты уже есть")
            return}

        val defaultSlots = mutableListOf<Lessons_slot>()
        val dayOfWeek = 1..7
        val startHour = 10
        val endHour = 18

        println("DEBUG: Начинаем создание слотов")

        for (day in dayOfWeek){
            for(hour in startHour until endHour){
                val startCalendar = Calendar.getInstance().apply{
                    clear()
                    set(Calendar.HOUR_OF_DAY,hour)
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
        val newCount = lessonDao.getAllSlots().size
        println("DEBUG: Создано слотов :${newCount}")
    }

    suspend fun getAllSlotsForDate(date:String):List<SlotUiModel>{
        val allSlots = lessonDao.getAllSlots()
        val bookedSlotId = lessonDao.getBookedSlotsId(date)

        val dayOfWeek = LocalDate.parse(date.trim()).dayOfWeek.value

        val daysSlots = allSlots.filter { it.day_of_week == dayOfWeek }

        return daysSlots.map {
            slot ->
            SlotUiModel(
                slot = slot,
                isBooked = bookedSlotId.contains(slot.id)
            )
        }
    }


    suspend fun initializeTestUser(){
        val users = userDao.getAllUsers().first()

        if(users.isEmpty()){

            val testUser = User(
            0,
            "save",
            "save",
            "save",
            "save",
            "2000-01-01",
            1000,
            "Test"
            )
            userDao.insert(testUser)
        }
        initializeDefaultSlots()
    }

}