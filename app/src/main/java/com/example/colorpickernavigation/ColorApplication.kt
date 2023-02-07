package com.example.colorpickernavigation

import android.app.Application
import android.content.Context
import com.example.colorpickernavigation.database.AppDatabase

class ColorApplication : Application() {
    fun getDB(context: Context): AppDatabase
    {
        val database: AppDatabase by lazy { AppDatabase.getDatabase(context) }
        return database
    }
}
