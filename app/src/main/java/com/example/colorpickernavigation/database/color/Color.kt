package com.example.colorpickernavigation.database.color

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

class Color {
    @Entity
    data class Color(
        @PrimaryKey val id: Int,
        @NonNull @ColumnInfo(name = "hex_code") val hexCode: String
    )
}