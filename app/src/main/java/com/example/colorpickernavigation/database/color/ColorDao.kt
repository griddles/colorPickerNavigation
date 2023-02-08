package com.example.colorpickernavigation.database.color

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ColorDao {
    @Query("SELECT * FROM colors")
    fun getAll(): List<Color>

    @Insert
    fun addColor(color: Color)

    @Delete
    fun remove(color: Color)
}
