package com.example.colorpickernavigation.database.color

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// a sort of object used for the database
@Entity(tableName = "colors")
data class Color(
    @ColumnInfo @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "color") val color: String
)