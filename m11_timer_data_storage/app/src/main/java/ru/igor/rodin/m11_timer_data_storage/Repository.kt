package ru.igor.rodin.m11_timer_data_storage

import android.content.Context
import android.content.Context.MODE_PRIVATE

class Repository(private val context: Context) {
    private var data: String? = null
    private val sharedPreferences by lazy {
        context.getSharedPreferences(
            context.getString(R.string.shared_pref_file),
            MODE_PRIVATE
        )
    }

    private fun getDataFromSharedPreferences(): String? =
        sharedPreferences.getString(context.getString(R.string.shared_pref_file), null)

    private fun getDataFromLocalVariables(): String? = data

    fun saveText(text: String) {
        data = text
        sharedPreferences.edit().apply {
            putString(context.getString(R.string.shared_pref_file), text)
            apply()
        }
    }

    fun clearText() {
        data = null
        sharedPreferences.edit().apply {
            clear()
            apply()
        }
    }

    fun getText(): String? = getDataFromLocalVariables() ?: getDataFromSharedPreferences()

}