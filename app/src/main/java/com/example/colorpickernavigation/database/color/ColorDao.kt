package com.example.colorpickernavigation.database.color

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ColorDao {
    @Query("SELECT * FROM color ORDER BY hex_code ASC")
    fun getAll(): List<Color>
}
