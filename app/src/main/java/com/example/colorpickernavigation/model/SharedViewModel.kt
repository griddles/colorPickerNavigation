package com.example.colorpickernavigation.model

import android.graphics.Color
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

    fun textVisible(color: Int): Int
    {
        // it's telling me that this doesn't change, it does lmao
        var hsv:FloatArray = floatArrayOf(0F, 0F, 0F)
        android.graphics.Color.colorToHSV(color, hsv)

        // new values for each part because editing the hsv array is illegal apparently
        var newHue = 0F
        var newSat = 0F
        var newLight = 0F

        // check the hue value (the sat and light values can't be invalid
        if (hsv[2] > 0.5)
        {
            newLight = 0F
        }
        else
        {
            newLight = 1F
        }

        // return the new color value
        val out:FloatArray = floatArrayOf(newHue, newSat, newLight)
        return android.graphics.Color.HSVToColor(out)
    }

    fun modifyColor(color: Int, hueChange: Int, satChange: Int, lightChange: Int): Int
    {
        // it's telling me that this doesn't change, it does lmao
        var hsv:FloatArray = floatArrayOf(0F, 0F, 0F)
        Color.colorToHSV(color, hsv)

        // new values for each part because editing the hsv array is illegal apparently
        var newHue = hsv[0] + hueChange
        var newSat = hsv[1] + (satChange.toFloat() / 100)
        var newLight = hsv[2] + (lightChange.toFloat() / 100)

        // check the hue value
        if (newHue > 360)
        {
            newHue -= 360
        }
        if (newHue < 0)
        {
            newHue += 360
        }
        // check the lightness value first because it can affect saturation
        if (newLight > 1F)
        {
            newLight = 1F
            newSat -= (lightChange.toFloat() / 100) // decreasing saturation appears to increase lightness when lightness is already at 100%
            // this isn't perfect since if lightness is at 86%, it'll set the new value to more than 100%, triggering this which then decreases
            // saturation by 15%, resulting in an effective ~29% lightness increase. It doesn't appear to be a noticeable difference, but it exists.
        }
        if (newLight < 0F)
        {
            newLight = 0F
        }
        // calculate the new saturation value
        if (newSat > 1F)
        {
            newSat = 1F
        }
        if (newSat < 0F)
        {
            newSat = 0F
        }

        // return the new color value
        val out:FloatArray = floatArrayOf(newHue, newSat, newLight)
        return Color.HSVToColor(out)
    }
}