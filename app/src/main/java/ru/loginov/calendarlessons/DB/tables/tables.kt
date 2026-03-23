package ru.loginov.calendarlessons.DB.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time
import java.util.Date

@Entity(tableName = "user")
data class User(
    @ColumnInfo(name = "user_id")
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val name:String,
    val lastname:String,
    val number: String,
    val password:String,
    val birthday:String,
    val balance:Int,
    val notice:String
)

@Entity(tableName = "lesson")
data class Lessons(
    @ColumnInfo(name = "lesson_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val date: Date,
    val time: Time,
    val userId:Int,
    val typeId:Int
)

@Entity(tableName = "type_lessons")
data class TypeLessons(
    @ColumnInfo(name = "type_id")
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val typeLesson:String,
    val price:Int,
    val describe: String
)

