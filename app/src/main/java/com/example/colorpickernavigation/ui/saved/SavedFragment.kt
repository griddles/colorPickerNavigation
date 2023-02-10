package com.example.colorpickernavigation.ui.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.colorpickernavigation.ColorApplication
import com.example.colorpickernavigation.RecyclerAdapter
import com.example.colorpickernavigation.database.color.Color
import com.example.colorpickernavigation.database.color.ColorDao
import com.example.colorpickernavigation.databinding.FragmentSavedBinding
import com.example.colorpickernavigation.model.ColorViewModel
import com.example.colorpickernavigation.model.SharedViewModel

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

        // initialize binding
        _binding = FragmentSavedBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.recyclerView

        // grab the current color value from the viewmodel in string form and in int form
        val colorString = sharedViewModel.getHex()
        val colorInt = android.graphics.Color.parseColor(colorString)

        // update the binding button with the current color and text that adapts to always be readable
        binding.addButt.setBackgroundColor(colorInt)
        binding.addButt.setTextColor(sharedViewModel.textVisible(colorInt))

        // get the database from the DAO
        colordb = ColorApplication().getDB(requireContext()).colorDao()
        val colorViewModel = ColorViewModel(colordb)

        binding.addButt.setOnClickListener()
        {
            // get all the colors from the database
            val colors = colorViewModel.getColors()
            // add a new color at the next index value
            colordb.addColor(Color(colors.count(), colorString))
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = RecyclerAdapter(colordb.getAll(), sharedViewModel, binding.addButt)

        return root
    }


}