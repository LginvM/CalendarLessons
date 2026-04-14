package ru.loginov.calendarlessons.DB.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.loginov.calendarlessons.DB.tables.Lessons
import ru.loginov.calendarlessons.DB.tables.Lessons_slot
import ru.loginov.calendarlessons.DB.tables.TypeLessons
import ru.loginov.calendarlessons.DB.tables.User
import java.util.Date

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM user WHERE number = :phone LIMIT 1")
    suspend fun getNumberAndPassword(phone: String): PhoneAndPassword?

    @Query("SELECT * FROM user")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT * FROM user WHERE user_id =:userId")
    fun getUser(userId:Int):Flow<User>
}

@Dao
interface TypeLessonDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(type: TypeLessons)

    @Update
    suspend fun update(type: TypeLessons)

    @Delete
    suspend fun delete(type: TypeLessons)

    @Query("SELECT * FROM type_lessons")
    fun getAllUsers(): Flow<List<TypeLessons>>

    @Query("SELECT * FROM type_lessons WHERE type_id =:typeId")
    fun getItems(typeId:Int):Flow<TypeLessons>
}

@Dao
interface LessonDao{

    //Получить все слоты
    @Query("SELECT * FROM lesson_slot ")
    suspend fun getAllSlots():List<Lessons_slot>



    //Получить занятые слоты на дату
    @Query("SELECT lesson_slot_id FROM lesson WHERE lesson_date=:date ")
    suspend fun getBookedSlotWithId(date:String):List<Int>



    //занятые слоты по id
    @Query("SELECT lesson_id FROM lesson WHERE lesson_date =:date ")
    suspend fun getBookedSlotsId(date:String):List<Int>



    //Все слоты по дате и id пользователя
    @Query("SELECT ls.* FROM lesson_slot ls " +
            "JOIN lesson l ON  ls.slot_id = l.lesson_slot_id " +
            "WHERE l.lesson_date =:date AND l.user_id =:userId")
    suspend fun getSlotByDateAndUserId(date:String,userId:Int):List<Lessons_slot>



    //записаться
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun bookLesson(lesson: Lesson)


}


data class User(
    @Embedded val user: User,

)
data class PhoneAndPassword(
    val number: String,
    val password: String
)

data class Lesson(
    val userId:Int,
    val lessonSlotId:Int,
    val date: String
)

