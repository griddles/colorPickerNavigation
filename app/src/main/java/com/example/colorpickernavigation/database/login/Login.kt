package com.example.colorpickernavigation.database.login

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// a sort of object used for the database
@Entity(tableName = "logins")
data class Login(
    @ColumnInfo @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "password") val password: String
)