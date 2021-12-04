package com.example.cryptocurrency.database

import androidx.room.TypeConverter
import java.time.Instant
import java.util.*

object Converter {

    @JvmStatic
    @TypeConverter
    fun fromDateToLong(date : Date):Long{
        return date.toInstant().epochSecond
    }

    @JvmStatic
    @TypeConverter
    fun fromLongToDate(timestamp: Long):Date{
        return Date.from(Instant.ofEpochSecond(timestamp))
    }
}