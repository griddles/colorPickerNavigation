package com.example.colorpickernavigation.database.color

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "colors")
data class Color(
    @ColumnInfo @PrimaryKey val id: Int?,
    @ColumnInfo(name = "color") val color: String
)