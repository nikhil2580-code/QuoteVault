package com.nikhilkhairnar.quotevault.helper

import android.content.Context
import com.nikhilkhairnar.quotevault.R

object UserSession {

    private const val PREF_NAME = "user_session"
    private const val KEY_USERNAME = "username"
    private const val KEY_AVATAR = "avatar"
    private const val KEY_LOGGED_IN = "logged_in"

    fun login(context: Context, username: String) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_USERNAME, username)
            .putInt(KEY_AVATAR, R.drawable.ic_avatar)
            .putBoolean(KEY_LOGGED_IN, true)
            .apply()
    }

    fun getUsername(context: Context): String {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getString(KEY_USERNAME, "Guest") ?: "Guest"
    }

    fun getAvatar(context: Context): Int {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getInt(KEY_AVATAR, R.drawable.ic_avatar)
    }

    fun isLoggedIn(context: Context): Boolean {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_LOGGED_IN, false)
    }

    fun logout(context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit()
            .clear()
            .apply()
    }
}
