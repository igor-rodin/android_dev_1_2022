package ru.igor.rodin.wordcounter

import android.app.Application
import androidx.room.Room
import ru.igor.rodin.wordcounter.room.WordsDao
import ru.igor.rodin.wordcounter.room.WordsDb

class WordsApp : Application() {
    lateinit var wordsDao: WordsDao
    override fun onCreate() {
        super.onCreate()

        val db = Room.databaseBuilder(applicationContext, WordsDb::class.java, "words.db")
            .build()
        wordsDao = db.wordsDao()
    }
}