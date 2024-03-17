package ru.igor.rodin.m13_databinding

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

private const val SEARCH_DELAY = 5000L

class SearchEngine() {
    suspend fun search(query: String): String? = withContext(Dispatchers.IO) {
        delay(SEARCH_DELAY)
        return@withContext null
    }
}