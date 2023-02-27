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
        val colorString = sharedViewModel.hexCode
        val colorInt = android.graphics.Color.parseColor(colorString)

        binding.addButt.setBackgroundColor(colorInt)
        binding.addButt.setTextColor(sharedViewModel.textVisible(colorInt))

        // get the database from the DAO
        colordb = ColorApplication().getDB(requireContext()).colorDao()

        binding.addButt.setOnClickListener()
        {
            // add a new color at the next index value
            colordb.addColor(Color(null, colorString, sharedViewModel.currentUid!!))

            refresh()
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = RecyclerAdapter(colordb.getAll(), sharedViewModel, colordb, this)

        return root
    }

    fun refresh()
    {
        parentFragmentManager.beginTransaction().detach(this).commitNow();
        parentFragmentManager.beginTransaction().attach(this).commitNow();
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}