package com.example.colorpickernavigation.ui.home

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.*
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.colorpickernavigation.databinding.FragmentHomeBinding
import com.example.colorpickernavigation.model.SharedViewModel
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import java.io.InputStream

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    val pickImage = 100
    lateinit var imageUri: Uri

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val sharedViewModel: SharedViewModel by activityViewModels()

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // references to each element
        val colorPickerView = binding.colorPickerView
        val alphaSlider = binding.alphaSlider
        val brightnessSlider = binding.brightnessSlider
        val alphaTile = binding.alphaTileView
        val hexCode = binding.hexCode
        val rgbCode = binding.rgbCode
        val copyHex = binding.copyHex
        val copyRGB = binding.copyRGB
        val editHexCode = binding.editHexCode
        val applyHexCode = binding.applyHexCode
        val cameraButton = binding.cameraButton
        // whether or not the color picker has initialized yet
        var listenerRan = false

        // listener that is called each time a new color is selected
        colorPickerView.setColorListener(ColorEnvelopeListener
        { _, _ ->
            // attach the two different sliders each frame (not sure if this is necessary but it sure works)
            colorPickerView.attachAlphaSlider(alphaSlider)
            colorPickerView.attachBrightnessSlider(brightnessSlider)
            // update the color tile
            alphaTile.setPaintColor(colorPickerView.color)
            // update the text displays
            hexCode.text = "#${colorPickerView.colorEnvelope.hexCode}"
            rgbCode.text = "${colorPickerView.colorEnvelope.argb[0]}-${colorPickerView.colorEnvelope.argb[1]}-${colorPickerView.colorEnvelope.argb[2]}-${colorPickerView.colorEnvelope.argb[3]}"

            // if this is the initialization cycle (the picker just got loaded), set the color saved in the ViewModel
            if (!listenerRan)
            {
                listenerRan = true // im pretty sure this is the worst way to do this
                initColor() // function this causes an error if the picker hasn't been loaded yet, so this is a thing
            }
            // update the value in the model
            sharedViewModel.setHex("#" + colorPickerView.colorEnvelope.hexCode)
        })

        // handle the copy buttons
        copyHex.setOnClickListener()
        {
            copyTextToClipboard(hexCode)
            Toast.makeText(context, "Copied Hex to Clipboard!", Toast.LENGTH_SHORT).show()
        }
        copyRGB.setOnClickListener()
        {
            copyTextToClipboard(rgbCode)
            Toast.makeText(context, "Copied RGB to Clipboard!", Toast.LENGTH_SHORT).show()
        }

        cameraButton.setOnClickListener()
        {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage) // deprecated :)
        }

        // handle the apply color button
        applyHexCode.setOnClickListener()
        {
            // ai generated regex :)
            val pattern = """^#([A-Fa-f\d]{6}|[A-Fa-f\d]{8})$"""
            val code = editHexCode.text.toString()
            val matcher = pattern.toRegex().matchEntire(code)
            // apply the regex to check if it's a proper hex code
            if (matcher != null) {
                val color = Color.parseColor(code)
                // set the color wheel to the appropriate color
                colorPickerView.selectByHsvColor(color)
            }
            else // otherwise, check if it's an RGB code
            {
                val parts = code.split("-")
                // differentiate between ARGB and RGB (could probably be done with a similar regex function)
                if (parts.size == 4)
                {
                    val alpha = Integer.parseInt(parts[0])
                    val red = Integer.parseInt(parts[1])
                    val green = Integer.parseInt(parts[2])
                    val blue = Integer.parseInt(parts[3])
                    val color = Color.argb(alpha, red, green, blue)
                    colorPickerView.selectByHsvColor(color)
                }
                else if (parts.size == 3)
                {
                    val red = Integer.parseInt(parts[0])
                    val green = Integer.parseInt(parts[1])
                    val blue = Integer.parseInt(parts[2])
                    val color = Color.rgb(red, green, blue)
                    colorPickerView.selectByHsvColor(color)
                }
            }
        }

        return root
    }

    // grab the color saved in the ViewModel to save the state of the picker when in the palette fragment
    private fun initColor() {
        val sharedViewModel: SharedViewModel by activityViewModels()
        val colorPickerView = binding.colorPickerView
        val color = Color.parseColor(sharedViewModel.getHex())
        colorPickerView.selectByHsvColor(color)
    }

    // copy text to clipboard
    private fun copyTextToClipboard(copyText: TextView)
    {
        val textToCopy = copyText.text
        val clipboardManager = activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("color", textToCopy)
        clipboardManager.setPrimaryClip(clipData)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data!!
            var drawable: Drawable
            val contentResolver = requireContext().contentResolver
            val inputStream: InputStream? = contentResolver.openInputStream(imageUri)
            drawable = Drawable.createFromStream(inputStream, imageUri.toString())!!

            binding.colorPickerView.setPaletteDrawable(drawable)
        }
    }

    // destroy the binding variable to not have a memory leak
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}