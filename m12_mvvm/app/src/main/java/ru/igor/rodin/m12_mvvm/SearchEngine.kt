package ru.igor.rodin.m12_mvvm

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

private const val SEARCH_DELAY = 4000L

class SearchEngine() {
    suspend fun search(query: String): String? = withContext(Dispatchers.IO) {
        delay(SEARCH_DELAY)
        return@withContext null
    }
}