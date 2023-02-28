package com.example.colorpickernavigation.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.colorpickernavigation.ColorApplication
import com.example.colorpickernavigation.MainActivity
import com.example.colorpickernavigation.R
import com.example.colorpickernavigation.database.login.Login
import com.example.colorpickernavigation.databinding.FragmentLoginBinding
import com.example.colorpickernavigation.model.LoginViewModel
import com.example.colorpickernavigation.model.SharedViewModel

class LoginFragment : Fragment()
{
    // set up binding
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val sharedViewModel: SharedViewModel by activityViewModels()
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // set up the different inputs and buttons
        val loginButton = binding.loginButton
        val registerButton = binding.registerButton
        val usernameInput = binding.usernameInput
        val passwordInput = binding.passwordInput

        // grab the main activity so that we can modify the bottom nav bar
        val mainActivity = activity as MainActivity?

        // handle the login button
        loginButton.setOnClickListener()
        {
            // check the login and then login, with feedback to the user
            if (checkLogin(usernameInput.text.toString(), passwordInput.text.toString()))
            {
                Toast.makeText(this.context, "Successfully Logged In!", Toast.LENGTH_SHORT).show()
                // hide the login button and show the logout button (mildly busted, dunno why)
                mainActivity!!.hideLoginButton()
                // set the current userID so that we know we're logged in
                sharedViewModel.currentUid = usernameInput.text.toString()
                // go home
                findNavController().navigate(R.id.navigation_home)
            }
            else
            {
                Toast.makeText(this.context, "Failed to Log In...", Toast.LENGTH_SHORT).show()
            }
        }

        // make a new account (easy)
        registerButton.setOnClickListener()
        {
            createLogin(usernameInput.text.toString(), passwordInput.text.toString())
        }

        return root
    }

    private fun checkLogin(username: String, password: String): Boolean
    {
        // get the database from the DAO
        val logindb = ColorApplication().getDB(requireContext()).loginDao()
        val loginViewModel = LoginViewModel(logindb)
        val logins = loginViewModel.getLogins()

        for (login: Login in logins)
        {
            // check the username first for security i think
            if (login.username == username)
            {
                // then check the password
                if (login.password == password)
                {
                    return true
                }
            }
        }

        return false
    }

    private fun createLogin(username: String, password: String)
    {
        if (username.contains(" ") || password.contains(" "))
        {
            Toast.makeText(this.context, "Info cannot contain spaces", Toast.LENGTH_SHORT).show()
            return
        }

        val logindb = ColorApplication().getDB(requireContext()).loginDao()
        logindb.addLogin(Login(null, username, password))

        Toast.makeText(this.context, "Created Account!", Toast.LENGTH_SHORT).show()
    }
}