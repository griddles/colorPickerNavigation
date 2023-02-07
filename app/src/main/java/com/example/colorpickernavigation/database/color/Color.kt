package com.example.colorpickernavigation.database.color

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "colors")
data class Color(
    @ColumnInfo @PrimaryKey val id: Int,
    @ColumnInfo(name = "colors") val color: String
)