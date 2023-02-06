package com.example.colorpickernavigation.model
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.colorpickernavigation.database.color.Color
import com.example.colorpickernavigation.database.color.ColorDao

class ColorViewModel(private val colorDao: ColorDao): ViewModel()
{
    fun fullSchedule(): List<Color> = colorDao.getAll()
}

class ColorViewModelFactory(
    private val colorDao: ColorDao
) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ColorViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ColorViewModel(colorDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
