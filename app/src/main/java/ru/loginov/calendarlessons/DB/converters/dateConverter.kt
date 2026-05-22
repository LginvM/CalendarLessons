package ru.loginov.calendarlessons.DB.converters

import androidx.room.TypeConverter
import java.sql.Time
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

class DateConverter{
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

    @TypeConverter
    fun fromLocalDate(date: LocalDate?):String?{
        return date?.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }

    @TypeConverter
    fun toLocalDate(dateString: String?):LocalDate?{
        return LocalDate.parse(dateString)
    }

//    @TypeConverter
//    fun fromTimestamp(value:Long?):Date?{
//        return value?.let{Date(it)}
//    }
//
//    @TypeConverter
//    fun dateToTimestamp(date:Date?):Long?{
//        return date?.time
//    }



}