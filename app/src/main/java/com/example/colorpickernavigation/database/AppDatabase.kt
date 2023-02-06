package com.example.colorpickernavigation.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.colorpickernavigation.database.color.Color
import com.example.colorpickernavigation.database.color.ColorDao

@Database(entities = arrayOf(Color::class), version = 1)
abstract class AppDatabase: RoomDatabase()
{
    abstract fun colorDao(): ColorDao

    companion object
    {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database")
                    .createFromAsset("database/colors.db")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}