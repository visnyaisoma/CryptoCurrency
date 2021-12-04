package com.example.cryptocurrency

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.navigation.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.cryptocurrency.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        val navController = findNavController(binding.fragment.id)
        val navbar = binding.bottomNavbar
        navbar.setOnItemSelectedListener {
            (it.onNavDestinationSelected(navController))

        }
        navbar.setupWithNavController(navController)
    }

}