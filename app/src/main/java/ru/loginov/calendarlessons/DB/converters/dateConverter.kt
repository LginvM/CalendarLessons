package ru.loginov.calendarlessons.DB.converters

import androidx.room.TypeConverter
import java.sql.Time
import java.util.Date

open class DateConverter{
    @TypeConverter
    fun toDate(date:Long?): Date? {
        return date?.let{ Date(it)}
    }

    @TypeConverter
    fun fromDate(date:Date?):Long?{
        return date?.time
    }

    @TypeConverter
    fun toTime(time:Long?): Time? {
        return time?.let{ Time(it)}
    }

    @TypeConverter
    fun fromTime(time:Time?):Long?{
        return time?.time
    }
}