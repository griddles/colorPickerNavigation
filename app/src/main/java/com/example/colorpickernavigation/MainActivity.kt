/*
Thaddeus Reimer
x/x/xx
Color Picker
 */

package com.example.colorpickernavigation

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.colorpickernavigation.databinding.ActivityMainBinding
import com.example.colorpickernavigation.databinding.AppBarMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    // real main activity binding
    private lateinit var binding: AppBarMainBinding

    // fake main activity binding
    private lateinit var finding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //supportActionBar?.hide()

        // setting up the bottom navigation bar
        binding = AppBarMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bottomNavView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_saved, R.id.navigation_login))
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavView.setupWithNavController(navController)
    }
}