package com.nikhilkhairnar.quotevault.helper

import android.content.Context

object SettingsPrefs {

    private const val PREF_NAME = "quote_settings"

    private const val KEY_DARK_MODE = "dark_mode"
    private const val KEY_FONT_SIZE = "font_size"
    private const val KEY_THEME = "theme"

    fun saveDarkMode(context: Context, enabled: Boolean) {
        prefs(context).edit().putBoolean(KEY_DARK_MODE, enabled).apply()
    }

    fun isDarkMode(context: Context): Boolean =
        prefs(context).getBoolean(KEY_DARK_MODE, true)

    fun saveFontSize(context: Context, size: Int) {
        prefs(context).edit().putInt(KEY_FONT_SIZE, size).apply()
    }

    fun getFontSize(context: Context): Int =
        prefs(context).getInt(KEY_FONT_SIZE, 18)

    fun saveTheme(context: Context, theme: String) {
        prefs(context).edit().putString(KEY_THEME, theme).apply()
    }

    fun getTheme(context: Context): String =
        prefs(context).getString(KEY_THEME, "blue")!!

    private fun prefs(context: Context) =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
}

