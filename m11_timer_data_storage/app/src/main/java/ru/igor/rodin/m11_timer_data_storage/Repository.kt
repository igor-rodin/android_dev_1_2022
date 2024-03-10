package ru.igor.rodin.m11_timer_data_storage

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log

class Repository(private val context: Context) {
    private var data: String? = null
    private fun getDataFromSharedPreferences(): String? {
        val sharedPref = context.getSharedPreferences(
            context.getString(R.string.shared_pref_file),
            MODE_PRIVATE
        )
        return sharedPref.getString(context.getString(R.string.shared_pref_file), null)
    }

    private fun getDataFromLocalVariables(): String? = data

    fun saveText(text: String) {
        data = text
        val sharedPref = context.getSharedPreferences(
            context.getString(R.string.shared_pref_file),
            MODE_PRIVATE
        )
        sharedPref.edit().apply {
            putString(context.getString(R.string.shared_pref_file), text)
            apply()
        }
    }

    fun clearText() {
        data = null
        val sharedPref = context.getSharedPreferences(
            context.getString(R.string.shared_pref_file),
            MODE_PRIVATE
        )
        sharedPref.edit().apply {
            clear()
            apply()
        }
    }

    fun getText(): String? = getDataFromLocalVariables() ?: getDataFromSharedPreferences()

}