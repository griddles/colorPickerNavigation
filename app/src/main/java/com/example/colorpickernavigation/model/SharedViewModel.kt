package com.example.colorpickernavigation.model

import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {
    private var hexCode: String = "#FFFFFFFF"



    // getters and setters because reasons
    fun setHex(hex: String)
    {
        hexCode = hex
    }
    fun getHex(): String
    {
        return hexCode
    }
}