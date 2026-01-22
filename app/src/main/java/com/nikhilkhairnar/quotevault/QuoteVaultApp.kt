package com.nikhilkhairnar.quotevault

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.nikhilkhairnar.quotevault.helper.SettingsPrefs

class QuoteVaultApp : Application() {
    override fun onCreate() {
        super.onCreate()

        val isDark = SettingsPrefs.isDarkMode(this)

        AppCompatDelegate.setDefaultNightMode(
            if (isDark)
                AppCompatDelegate.MODE_NIGHT_YES
            else
                AppCompatDelegate.MODE_NIGHT_NO
        )
    }
}
