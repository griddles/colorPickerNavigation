package com.example.colorpickernavigation.ui.palette

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.colorpickernavigation.databinding.FragmentPaletteBinding
import com.example.colorpickernavigation.model.SharedViewModel

class PaletteFragment : Fragment() {

    private var _binding: FragmentPaletteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val sharedViewModel =
            ViewModelProvider(this).get(SharedViewModel::class.java)

        _binding = FragmentPaletteBinding.inflate(inflater, container, false)
        val root: View = binding.root
        // -----------------------------------------------------------------------------------------

        binding.textDashboard.text = sharedViewModel.hexCode

        // -----------------------------------------------------------------------------------------
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}