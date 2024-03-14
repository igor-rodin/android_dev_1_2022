package ru.igor.rodin.m13_databinding

import android.app.Application
import android.content.Context

class SearchApp : Application() {
    companion object {
        private lateinit var instance: SearchApp

        fun getContext(): Context = instance.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}