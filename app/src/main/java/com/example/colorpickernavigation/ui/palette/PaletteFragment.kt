package com.example.colorpickernavigation.ui.palette

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
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

        val color = Color.parseColor(sharedViewModel.getHex())
        // text visibility
        binding.blackText.setBackgroundColor(color)
        binding.whiteText.setBackgroundColor(color)
        binding.blackBackground.setTextColor(color)
        binding.whiteBackground.setTextColor(color)

        // darker/lighter
        binding.blackDarkerColor.setBackgroundColor(modifyColor(color, 0, 0, -15))
        binding.blackStandardColor.setBackgroundColor(color)
        binding.blackLighterColor.setBackgroundColor(modifyColor(color, 0, 0, 15))
        binding.whiteDarkerColor.setBackgroundColor(modifyColor(color, 0, 0, -15))
        binding.whiteStandardColor.setBackgroundColor(color)
        binding.whiteLighterColor.setBackgroundColor(modifyColor(color, 0, 0, 15))

        // complimentary
        binding.defaultComplimentaryColor.setBackgroundColor(color)
        binding.complimentaryColor.setBackgroundColor(modifyColor(color, 180, 0, 0))

        // triadic
        binding.triadicLeftColor.setBackgroundColor(modifyColor(color, -120, 0, 0))
        binding.triadicDefaultColor.setBackgroundColor(color)
        binding.triadicRightColor.setBackgroundColor(modifyColor(color, 120, 0, 0))

        // make the API call to get the name of the color
        run("https://www.thecolorapi.com/id?hex=${sharedViewModel.getHex().drop(3)}")

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
                // get the string value of the color's name
                val colorName = response.getJSONObject("name").getString("value")
                // as far as I can tell you have to set the values here since this doesn't get executed immediately
                binding.blackText.text = "Black on $colorName"
                binding.whiteText.text = "White on $colorName"
                binding.blackBackground.text = "$colorName on Black"
                binding.whiteBackground.text = "$colorName on White"
            },
            { error ->
                // do nothing since error handling is somewhere else
            }
        )

        // execute the request
        queue.add(jsonObjectRequest)
    }

    private fun modifyColor(color: Int, hueChange: Int, satChange: Int, lightChange: Int): Int
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}