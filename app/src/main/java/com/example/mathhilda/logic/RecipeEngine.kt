package com.example.mathhilda.logic

import com.example.mathhilda.model.Difficulty
import com.example.mathhilda.model.GameEngine
import com.example.mathhilda.model.GameTask
import kotlin.random.Random

class RecipeEngine : GameEngine {
    override fun generateTask(difficulty: Difficulty): GameTask {
        val (basePersons, targetPersons) = when (difficulty) {
            Difficulty.EASY -> {
                val b = 2
                val t = listOf(1, 4, 6).random()
                b to t
            }
            Difficulty.MEDIUM -> {
                val b = 4
                val t = listOf(2, 6, 8, 10).random()
                b to t
            }
            Difficulty.HARD -> {
                val b = listOf(3, 5, 6).random()
                val t = listOf(2, 4, 7, 9).random()
                b to t
            }
        }

        val baseAmount = when (difficulty) {
            Difficulty.EASY -> Random.nextInt(1, 10) * 100
            Difficulty.MEDIUM -> Random.nextInt(5, 20) * 50
            Difficulty.HARD -> Random.nextInt(10, 50) * 25
        }

        // Result = (baseAmount / basePersons) * targetPersons
        // Ensure result is an integer for simplicity
        val result = (baseAmount.toDouble() / basePersons) * targetPersons
        
        // If result is not whole, retry once or just round
        val finalResult = result.toInt()

        val unit = listOf("g", "ml").random()
        val ingredient = listOf("Mehl", "Zucker", "Milch", "Butter", "Wasser").random()

        val question = "Rezept für $basePersons Personen:\n$baseAmount$unit $ingredient.\nWie viel für $targetPersons Personen?"
        return GameTask(question, finalResult.toString())
    }
}