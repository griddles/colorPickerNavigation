package com.example.colorpickernavigation.ui.saved

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.coroutineScope
import com.example.colorpickernavigation.ColorApplication
import com.example.colorpickernavigation.database.color.Color
import com.example.colorpickernavigation.database.color.ColorDao
import com.example.colorpickernavigation.databinding.FragmentSavedBinding
import com.example.colorpickernavigation.model.ColorViewModel
import com.example.colorpickernavigation.model.SharedViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SavedFragment : Fragment()
{
    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!

    private lateinit var colordb: ColorDao

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val sharedViewModel: SharedViewModel by activityViewModels()


        _binding = FragmentSavedBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val colorString = sharedViewModel.getHex()
        val colorInt = android.graphics.Color.parseColor(colorString)

        binding.addButt.setBackgroundColor(colorInt)
        binding.addButt.setTextColor(textVisible(colorInt))

        colordb = ColorApplication().getDB(requireContext()).colorDao()

        val colorViewModel = ColorViewModel(colordb)

        binding.addButt.setOnClickListener()
        {
            val colors = colorViewModel.getColors()
            colordb.addColor(Color(colors.count(), colorString))
        }

        return root
    }

    private fun textVisible(color: Int): Int
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
}