package ru.igor.rodin.wordcounter.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class WordEntity(
    @PrimaryKey
    @ColumnInfo(name = "word")
    val word: String,
    @ColumnInfo(name = "count")
    val count: Int = 1
)