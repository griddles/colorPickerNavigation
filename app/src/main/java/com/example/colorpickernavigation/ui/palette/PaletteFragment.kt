package com.example.colorpickernavigation.ui.palette

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.colorpickernavigation.databinding.FragmentPaletteBinding
import com.example.colorpickernavigation.model.SharedViewModel

class PaletteFragment : Fragment() {

    private var _binding: FragmentPaletteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val sharedViewModel: SharedViewModel by activityViewModels()

        _binding = FragmentPaletteBinding.inflate(inflater, container, false)
        val root: View = binding.root
        // -----------------------------------------------------------------------------------------

        val color = Color.parseColor(sharedViewModel.hexCode)
        // text visibility
        binding.blackText.setBackgroundColor(color)
        binding.whiteText.setBackgroundColor(color)
        binding.blackBackground.setTextColor(color)
        binding.whiteBackground.setTextColor(color)

        // darker/lighter
        binding.blackDarkerColor.setBackgroundColor(sharedViewModel.modifyColor(color, 0, 0, -15))
        binding.blackStandardColor.setBackgroundColor(color)
        binding.blackLighterColor.setBackgroundColor(sharedViewModel.modifyColor(color, 0, 0, 15))
        binding.whiteDarkerColor.setBackgroundColor(sharedViewModel.modifyColor(color, 0, 0, -15))
        binding.whiteStandardColor.setBackgroundColor(color)
        binding.whiteLighterColor.setBackgroundColor(sharedViewModel.modifyColor(color, 0, 0, 15))

        // complimentary
        binding.defaultComplimentaryColor.setBackgroundColor(color)
        binding.complimentaryColor.setBackgroundColor(sharedViewModel.modifyColor(color, 180, 0, 0))

        // triadic
        binding.triadicLeftColor.setBackgroundColor(sharedViewModel.modifyColor(color, -120, 0, 0))
        binding.triadicDefaultColor.setBackgroundColor(color)
        binding.triadicRightColor.setBackgroundColor(sharedViewModel.modifyColor(color, 120, 0, 0))

        // make the API call to get the name of the color
        run("https://www.thecolorapi.com/id?hex=${sharedViewModel.hexCode.drop(3)}")

        // -----------------------------------------------------------------------------------------
        return root
    }

    @SuppressLint("SetTextI18n")
    fun run(url: String)
    {
        // set up the queue with volley
        val queue = Volley.newRequestQueue(context)

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                try
                {
                    // get the string value of the color's name
                    val colorName = response.getJSONObject("name").getString("value")
                    // as far as I can tell you have to set the values here since this doesn't get executed immediately
                    binding.blackText.text = "Black on $colorName"
                    binding.whiteText.text = "White on $colorName"
                    binding.blackBackground.text = "$colorName on Black"
                    binding.whiteBackground.text = "$colorName on White"
                }
                catch (e: NullPointerException)
                {
                    // do nothing
                }
            },
            { e ->
                // do nothing since error handling is somewhere else
            }
        )

        // execute the request
        queue.add(jsonObjectRequest)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}