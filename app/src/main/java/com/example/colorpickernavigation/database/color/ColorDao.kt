package com.example.colorpickernavigation.database.color

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ColorDao {
    @Query("SELECT * FROM colors")
    fun getAll(): Flow<List<Color>>
}
