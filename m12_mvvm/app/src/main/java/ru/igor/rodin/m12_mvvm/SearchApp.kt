package ru.igor.rodin.m12_mvvm

import android.app.Application

class SearchApp : Application() {
    companion object {
        private lateinit var instance: SearchApp

        fun getContext() = instance.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}