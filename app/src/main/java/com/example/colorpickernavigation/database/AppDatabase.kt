package com.example.colorpickernavigation.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.colorpickernavigation.database.color.Color
import com.example.colorpickernavigation.database.color.ColorDao
import com.example.colorpickernavigation.database.login.Login
import com.example.colorpickernavigation.database.login.LoginDao

@Database(entities = [Color::class, Login::class], version = 1)
abstract class AppDatabase: RoomDatabase()
{
    companion object
    {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // database initialization and stuff
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database")
                    .createFromAsset("database/colors.sql")
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }

    abstract fun colorDao(): ColorDao
    abstract fun loginDao(): LoginDao
}
