package com.example.colorpickernavigation.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.colorpickernavigation.database.color.Color
import com.example.colorpickernavigation.database.color.ColorDao
import com.example.colorpickernavigation.database.login.Login
import com.example.colorpickernavigation.database.login.LoginDao

class LoginViewModel(private val loginDao: LoginDao): ViewModel()
{
    fun getLogins(): List<Login> = loginDao.getAll()
}

// to be completely honest I have no clue what this is doing
class LoginViewModelFactory(
    private val loginDao: LoginDao
) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java))
        {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(loginDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

