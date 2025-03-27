package com.example.weather.utils

import android.content.Context
import android.util.Base64


class SharedPreferencesUtil(context: Context) {

    companion object{
        const val CITY_NAME = "city_name"
    }

    private val preferences = context.getSharedPreferences("WEATHER_PREFERENCE",Context.MODE_PRIVATE)

    fun setString(key: String, value: String){
        val editor = preferences.edit()
        editor.putString(encryptString(key), value)
        value?.let { editor.apply() } ?: editor.remove(key)
    }

    fun getString(key: String, defValue: String): String{
        checkForOldStringKey(key)
        return preferences.getString(encryptString(key), defValue)  ?: defValue
    }

    private fun checkForOldStringKey(key: String){
        if(preferences.contains(key)){
            val oldValue = preferences.getString(key, "")
            if (oldValue != null) {
                setString(key, oldValue)
            }
            preferences.edit().remove(key).apply()
        }
    }

    private fun encryptString(input: String?): String? {
        input?.let {
            return Base64.encodeToString(input.toByteArray(), Base64.DEFAULT)
        } ?: run {
            return null
        }
    }

}