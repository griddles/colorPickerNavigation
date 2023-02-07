package com.example.colorpickernavigation.ui.saved

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.colorpickernavigation.R
import com.example.colorpickernavigation.database.color.Color
import com.example.colorpickernavigation.database.color.ColorDao
import com.example.colorpickernavigation.databinding.FragmentSavedBinding
import com.example.colorpickernavigation.model.ColorViewModel
import com.example.colorpickernavigation.model.SharedViewModel

class SavedFragment : Fragment()
{
    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val sharedViewModel: SharedViewModel by activityViewModels()
        val colorViewModel: ColorViewModel by activityViewModels()

        _binding = FragmentSavedBinding.inflate(inflater, container, false)
        val root: View = binding.root

        

        // code goes here

        return root
    }

    private fun loadPreference(k:String): String
    {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val pref = sharedPref?.getString(k, null)

        return pref!!
    }

    private fun savePreference(k:String, v:String)
    {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit())
        {
            putString(k, v)
            apply()
        }
    }
}