package com.example.mathhilda.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mathhilda.model.Difficulty
import com.example.mathhilda.model.GameResult
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Insert
    suspend fun insertResult(result: GameResult)

    @Query("SELECT * FROM game_results ORDER BY timestamp DESC")
    fun getAllResults(): Flow<List<GameResult>>

    @Query("SELECT MAX(score) FROM game_results WHERE gameType = :gameType AND difficulty = :difficulty")
    suspend fun getBestScore(gameType: String, difficulty: Difficulty): Int?

    @Query("SELECT MAX(correctAnswers) FROM game_results WHERE gameType = :gameType AND difficulty = :difficulty")
    suspend fun getBestCorrectCount(gameType: String, difficulty: Difficulty): Int?
}