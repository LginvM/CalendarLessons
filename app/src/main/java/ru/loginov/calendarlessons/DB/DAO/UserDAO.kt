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
import ru.loginov.calendarlessons.DB.tables.TypeLessons
import ru.loginov.calendarlessons.DB.tables.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(lesson: Lessons)


}

data class User(
    @Embedded val user: User,
)