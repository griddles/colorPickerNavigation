package com.example.colorpickernavigation.database.login

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

// the DAO that handles database queries and other operations
@Dao
interface LoginDao {
    @Query("SELECT * FROM logins")
    fun getAll(): List<Login>

    @Insert
    fun addLogin(login: Login)

    @Delete
    fun remove(login: Login)
}
