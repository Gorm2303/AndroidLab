package com.example.androidlab

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(Converters::class)
@Database(entities = [Movie :: class], version = 6)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao() : MovieDao

    companion object {
        @Volatile
        private var INSTANCE : AppDatabase? = null

        fun getDatabase (context : Context): AppDatabase {
            synchronized(this) {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}