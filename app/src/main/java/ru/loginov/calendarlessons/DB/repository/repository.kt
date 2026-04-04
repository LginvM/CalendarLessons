package ru.loginov.calendarlessons.DB.repository

import kotlinx.coroutines.flow.Flow
import ru.loginov.calendarlessons.DB.DAO.LessonDao
import ru.loginov.calendarlessons.DB.DAO.PhoneAndPassword
import ru.loginov.calendarlessons.DB.DAO.TypeLessonDao
import ru.loginov.calendarlessons.DB.DAO.UserDao
import ru.loginov.calendarlessons.DB.tables.User

class Repository(
    private val userDao: UserDao,
    private val typeLessonDao: TypeLessonDao,
    private val lessonDao: LessonDao
){
    val getAllUser = userDao.getAllUsers()

    fun getPhoneAndPassword(phone: String, password: String): Flow<PhoneAndPassword?> {
        return userDao.getNumberAndPassword(phone, password)
    }

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
}