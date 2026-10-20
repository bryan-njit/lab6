package com.codepath.lab6

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.codepath.lab6.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.serialization.json.Json

// Helper function for JSON parsing
fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}

private const val TAG = "MainActivity/"
private const val SELECTED_TAB = "selected_tab"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // load saved settings before the screen is drawn
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        applyDarkMode(prefs.getBoolean(DARK_MODE, false))

        super.onCreate(savedInstanceState)

        // Set up ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val homeFragment = HomeFragment()
        val parksFragment = ParksFragment()
        val campgroundFragment = CampgroundFragment()
        val settingsFragment = SettingsFragment()

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        // swap the fragment when a tab is picked
        bottomNavigationView.setOnItemSelectedListener { item ->
            lateinit var fragment: Fragment
            when (item.itemId) {
                R.id.nav_home -> fragment = homeFragment
                R.id.nav_parks -> fragment = parksFragment
                R.id.nav_campgrounds -> fragment = campgroundFragment
                R.id.nav_settings -> fragment = settingsFragment
            }
            replaceFragment(fragment)
            // show which tab we're on under the app name
            supportActionBar?.subtitle = item.title
            true
        }

        // Set default selection, or go back to the tab we were on if the activity got recreated
        val startTab = if (prefs.getBoolean(START_ON_PARKS, false)) R.id.nav_parks else R.id.nav_home
        bottomNavigationView.selectedItemId = savedInstanceState?.getInt(SELECTED_TAB) ?: startTab
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTED_TAB, binding.bottomNavigation.selectedItemId)
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_frame_layout, fragment)
        fragmentTransaction.commit()
    }
}
