/*
Thaddeus Reimer
x/x/xx
Color Picker
 */

package com.example.colorpickernavigation

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.colorpickernavigation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //supportActionBar?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        // initialize navigation
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_home, R.id.navigation_palette, R.id.navigation_saved, R.id.navigation_login, R.id.navigation_logout)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.menu.getItem(4).isVisible = false
    }

    fun hideLoginButton()
    {
        binding.navView.menu.getItem(3).isVisible = false
        binding.navView.menu.getItem(4).isVisible = true
    }

    fun showLoginButton()
    {
        binding.navView.menu.getItem(3).isVisible = true
        binding.navView.menu.getItem(4).isVisible = false
    }
}