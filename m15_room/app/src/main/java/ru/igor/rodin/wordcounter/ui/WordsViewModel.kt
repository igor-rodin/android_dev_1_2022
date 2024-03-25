package ru.igor.rodin.wordcounter.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.launch
import ru.igor.rodin.wordcounter.WordsApp
import ru.igor.rodin.wordcounter.room.WordsDao

class WordsViewModel(private val wordsDao: WordsDao) : ViewModel() {

    fun insertOrUpdate(word: String) {
        viewModelScope.launch {
            wordsDao.insertOrUpdate(word)
        }
    }

    fun clear() {
        viewModelScope.launch {
            wordsDao.clear() }
    }

    companion object {
        val Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val app = requireNotNull(extras[APPLICATION_KEY]) as WordsApp
                return WordsViewModel(app.wordsDao) as T
            }
        }
    }
}