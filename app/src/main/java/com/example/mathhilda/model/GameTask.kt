package com.example.mathhilda.model

data class GameTask(
    val question: String,
    val correctAnswer: String,
    val displayInfo: String = "" // Optional extra info (e.g. image name or hint)
)

interface GameEngine {
    fun generateTask(difficulty: Difficulty): GameTask
}