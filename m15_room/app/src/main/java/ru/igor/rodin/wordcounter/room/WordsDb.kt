package ru.igor.rodin.wordcounter.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WordEntity::class], version = 1)
abstract class WordsDb : RoomDatabase() {
    abstract fun wordsDao(): WordsDao
}