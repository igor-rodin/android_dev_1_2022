package ru.igor.rodin.m13_databinding.common

import android.content.Context
import androidx.annotation.StringRes

class StringResourceHelper(private val context: Context) {
    fun getString(@StringRes id: Int, vararg args: Any): String = context.getString(id, *args)
}