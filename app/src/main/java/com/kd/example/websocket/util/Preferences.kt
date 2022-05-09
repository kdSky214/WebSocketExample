package com.kd.example.websocket.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object Preferences {
    private const val KEY_DATA = "DATA"
    private const val KEY_ACCESS_TOKEN = "ACCESS_TOKEN"
    private lateinit var pref: SharedPreferences
    fun init(context:Context){
        pref =  context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
    }
    var accessToken: String
        get() = pref.getString(KEY_ACCESS_TOKEN, "") ?: ""
        set(value) = pref.edit() {
            putString(KEY_ACCESS_TOKEN, value)
        }
}