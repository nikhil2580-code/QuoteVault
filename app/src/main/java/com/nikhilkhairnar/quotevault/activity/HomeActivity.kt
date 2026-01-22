package com.nikhilkhairnar.quotevault.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.nikhilkhairnar.quotevault.R
import com.nikhilkhairnar.quotevault.fragments.CategoriesFragment
import com.nikhilkhairnar.quotevault.fragments.FavoritesFragment
import com.nikhilkhairnar.quotevault.fragments.HomeFragment
import com.nikhilkhairnar.quotevault.fragments.ProfileFragment
import com.nikhilkhairnar.quotevault.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nikhilkhairnar.quotevault.fragments.SettingsFragment
import com.nikhilkhairnar.quotevault.helper.SettingsPrefs
import com.nikhilkhairnar.quotevault.helper.SupabaseClient

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (SupabaseClient.auth.currentSessionOrNull() == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> loadFragment(HomeFragment())
                R.id.nav_categories -> loadFragment(CategoriesFragment())
                R.id.nav_favorites -> loadFragment(FavoritesFragment())
                R.id.nav_setting -> loadFragment(SettingsFragment())
                R.id.nav_profile -> loadFragment(ProfileFragment())
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
