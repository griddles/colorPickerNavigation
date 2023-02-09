package com.example.colorpickernavigation.database.color

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

// the DAO that handles database queries and other operations
@Dao
interface ColorDao {
    @Query("SELECT * FROM colors")
    fun getAll(): List<Color>

    @Insert
    fun addColor(color: Color)

    @Delete
    fun remove(color: Color)
}
