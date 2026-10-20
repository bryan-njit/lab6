package com.codepath.lab6

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat

const val PREFS_NAME = "settings"
const val DARK_MODE = "dark_mode"
const val START_ON_PARKS = "start_on_parks"

fun applyDarkMode(on: Boolean) {
    AppCompatDelegate.setDefaultNightMode(
        if (on) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
    )
}

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        val darkModeSwitch: SwitchCompat = view.findViewById(R.id.dark_mode_switch)
        darkModeSwitch.isChecked = prefs.getBoolean(DARK_MODE, false)
        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean(DARK_MODE, isChecked).apply()
            applyDarkMode(isChecked)
        }

        val startOnParksSwitch: SwitchCompat = view.findViewById(R.id.start_parks_switch)
        startOnParksSwitch.isChecked = prefs.getBoolean(START_ON_PARKS, false)
        startOnParksSwitch.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean(START_ON_PARKS, isChecked).apply()
        }

        return view
    }

    companion object {
        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }
}
