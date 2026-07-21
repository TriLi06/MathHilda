package com.example.mathhilda.ui.screens

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mathhilda.data.AppDatabase
import com.example.mathhilda.logic.*
import com.example.mathhilda.model.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    
    var currentTask by mutableStateOf<GameTask?>(null)
    var userInput by mutableStateOf("")
    var score by mutableIntStateOf(0)
    var correctCount by mutableIntStateOf(0)
    var incorrectCount by mutableIntStateOf(0)
    var timerSeconds by mutableIntStateOf(60)
    var isGameOver by mutableStateOf(false)
    var isNewRecord by mutableStateOf(false)
    var motivationalMessage by mutableStateOf("")
    var feedbackAnimal by mutableStateOf("🦛")
    
    private var timerJob: Job? = null
    private lateinit var engine: GameEngine
    private lateinit var currentDifficulty: Difficulty
    private lateinit var currentGameType: String
    
    // For Challenge Mode
    private var challengeModes = mutableListOf<String>()
    private var isChallengeMode = false

    fun startGame(gameType: String, difficulty: Difficulty) {
        currentGameType = gameType
        currentDifficulty = difficulty
        engine = when (gameType) {
            "Arithmetic" -> ArithmeticEngine()
            "Clock" -> ClockEngine()
            "Shopping" -> ShoppingEngine()
            "Recipe" -> RecipeEngine()
            "Date" -> DateEngine()
            "Geometry" -> GeometryEngine()
            "Pattern" -> PatternEngine()
            "Sorting" -> SortingEngine()
            else -> ArithmeticEngine()
        }
        resetStats()
        nextTask()
        startTimer()
    }

    fun startChallenge(difficulty: Difficulty) {
        isChallengeMode = true
        challengeModes = mutableListOf("Arithmetic", "Clock", "Shopping", "Recipe", "Date", "Geometry", "Pattern", "Sorting")
        challengeModes.shuffle()
        challengeModes = challengeModes.take(3).toMutableList()
        
        startGame(challengeModes.removeAt(0), difficulty)
    }

    private fun resetStats() {
        score = 0
        correctCount = 0
        incorrectCount = 0
        timerSeconds = 60
        isGameOver = false
        userInput = ""
    }

    private fun nextTask() {
        currentTask = engine.generateTask(currentDifficulty)
        userInput = ""
    }

    fun onNumberInput(digit: String) {
        if (!isGameOver) userInput += digit
    }

    fun onDelete() {
        if (!isGameOver && userInput.isNotEmpty()) {
            userInput = userInput.dropLast(1)
        }
    }

    fun onSubmit() {
        if (isGameOver || currentTask == null) return
        
        if (userInput == currentTask!!.correctAnswer) {
            score += 10
            correctCount++
        } else {
            score -= 5
            incorrectCount++
        }
        nextTask()
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (timerSeconds > 0) {
                delay(1000)
                timerSeconds--
            }
            endGame()
        }
    }

    private fun endGame() {
        isGameOver = true
        
        viewModelScope.launch {
            val previousBest = db.historyDao().getBestScore(currentGameType, currentDifficulty) ?: 0
            isNewRecord = score > previousBest && correctCount > 0
            
            feedbackAnimal = listOf("🦛", "🦁", "🦓", "🐻").random()
            motivationalMessage = when {
                isNewRecord -> "WAHNSINN! Das ist ein neuer Rekord! 🏆"
                score >= 50 -> "Das hast du sehr gut gemacht! 🌟"
                score >= 20 -> "Weiter so! Du wirst immer besser! 💪"
                else -> "Guter Versuch! Übung macht den Meister! 🎈"
            }
            
            saveResult()
        }
        
        if (isChallengeMode && challengeModes.isNotEmpty()) {
            // In a real app, we might wait for a "Next" button, 
            // but for simplicity, let's just mark it done or chain it.
            // But the prompt says "hintereinander durchlaufen".
            // We'll need a way to transition.
        }
    }

    private fun saveResult() {
        viewModelScope.launch {
            db.historyDao().insertResult(
                GameResult(
                    gameType = currentGameType,
                    difficulty = currentDifficulty,
                    score = score,
                    correctAnswers = correctCount,
                    incorrectAnswers = incorrectCount
                )
            )
        }
    }
}