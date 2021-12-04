package com.example.cryptocurrency.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [CoinTransaction::class, CoinHolding::class],version = 1,exportSchema = false)
@TypeConverters(Converter::class)
abstract class HoldingsDatabase : RoomDatabase(){

    abstract fun dao() : Dao

    companion object{
        @Volatile
        private var INSTANCE: HoldingsDatabase? = null

        fun getDataBase(context: Context): HoldingsDatabase {
            synchronized(lock = this){
                val tmp = INSTANCE
                if(tmp!=null){
                    return tmp
                }
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HoldingsDatabase::class.java,
                    "holdings_database",
                ).build()
                INSTANCE =instance
                return instance
            }
        }
    }
}