package com.example.mathhilda.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_results")
data class GameResult(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val gameType: String,
    val difficulty: Difficulty,
    val score: Int,
    val correctAnswers: Int,
    val incorrectAnswers: Int,
    val timestamp: Long = System.currentTimeMillis()
)