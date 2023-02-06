package com.example.colorpickernavigation

import android.app.Application
import com.example.colorpickernavigation.database.AppDatabase

class ColorApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}
