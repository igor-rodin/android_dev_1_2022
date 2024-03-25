package ru.igor.rodin.wordcounter

import android.app.Application
import androidx.room.Room
import ru.igor.rodin.wordcounter.room.WordsDao
import ru.igor.rodin.wordcounter.room.WordsDb

class WordsApp : Application() {
    lateinit var wordsDao: WordsDao
    override fun onCreate() {
        super.onCreate()

        val db = Room.inMemoryDatabaseBuilder(applicationContext, WordsDb::class.java)
            .fallbackToDestructiveMigration()
            .build()
        wordsDao = db.wordsDao()
    }
}