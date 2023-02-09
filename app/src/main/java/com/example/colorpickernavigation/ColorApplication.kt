package com.example.colorpickernavigation

import android.app.Application
import android.content.Context
import com.example.colorpickernavigation.database.AppDatabase

class ColorApplication : Application() {
    // why is everything so split up...
    // we could have just done it in like 2 but noooo we **need** to have 6
    fun getDB(context: Context): AppDatabase
    {
        val database: AppDatabase by lazy { AppDatabase.getDatabase(context) }
        return database
    }
}
