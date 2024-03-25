package ru.igor.rodin.wordcounter.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

private const val DEFAULT_SIZE = 5

@Dao
interface WordsDao {

    @Query("SELECT * FROM words ORDER BY count DESC LIMIT :limit")
    fun getFirstMostCommon(limit: Int = DEFAULT_SIZE): Flow<List<WordEntity>>

    @Transaction
    suspend fun insertOrUpdate(word: String) {
        val prevCont = _checkWord(word)
        if (prevCont == 0) {
            _insertWord(WordEntity(word = word))
        } else {
            _updateWord(word)
        }
    }

    @Query("DELETE FROM words")
    suspend fun clear()

    @Query("SELECT * FROM words WHERE word = :word")
    suspend fun _checkWord(word: String): Int

    @Insert
    suspend fun _insertWord(word: WordEntity)

    @Query("UPDATE words SET count = count + 1 WHERE word = :word")
    suspend fun _updateWord(word: String)
}