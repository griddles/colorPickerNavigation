package com.example.colorpickernavigation.ui.logout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.colorpickernavigation.MainActivity
import com.example.colorpickernavigation.R
import com.example.colorpickernavigation.model.SharedViewModel

class LogoutFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val sharedViewModel: SharedViewModel by activityViewModels()

        sharedViewModel.currentUid = null
        val mainActivity = activity as MainActivity?
        mainActivity!!.showLoginButton()

        findNavController().navigate(R.id.navigation_login)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logout, container, false)
    }
}