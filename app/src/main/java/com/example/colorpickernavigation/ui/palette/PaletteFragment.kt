package com.example.colorpickernavigation.ui.palette

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.colorpickernavigation.databinding.FragmentPaletteBinding
import com.example.colorpickernavigation.model.SharedViewModel
import kotlin.math.log

class PaletteFragment : Fragment() {

    private var _binding: FragmentPaletteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    var output = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val sharedViewModel: SharedViewModel by activityViewModels()

        _binding = FragmentPaletteBinding.inflate(inflater, container, false)
        val root: View = binding.root
        // -----------------------------------------------------------------------------------------

        val color = Color.parseColor(sharedViewModel.getHex())
        binding.paletteTop.setBackgroundColor(color)
        binding.paletteBottom.setBackgroundColor(color)

        run("https://www.thecolorapi.com/id?hex=${sharedViewModel.getHex().drop(3)}")

        // -----------------------------------------------------------------------------------------
        return root
    }

    fun run(url: String)
    {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                binding.paletteTop.text = response.toString().split("\"name\":{\"value\":\"")[1].split("\"")[0]
                binding.paletteBottom.text = response.toString().split("\"name\":{\"value\":\"")[1].split("\"")[0]
            },
            {
                output = "That didn't work!"
            })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}