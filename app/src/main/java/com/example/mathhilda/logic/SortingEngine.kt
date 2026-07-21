package com.example.mathhilda.logic

import com.example.mathhilda.model.Difficulty
import com.example.mathhilda.model.GameEngine
import com.example.mathhilda.model.GameTask
import kotlin.random.Random

class SortingEngine : GameEngine {
    override fun generateTask(difficulty: Difficulty): GameTask {
        return if (Random.nextBoolean()) generateComparisonTask(difficulty)
        else generateUnitTask(difficulty)
    }

    private fun generateComparisonTask(difficulty: Difficulty): GameTask {
        val count = when (difficulty) {
            Difficulty.EASY -> 3
            Difficulty.MEDIUM -> 4
            Difficulty.HARD -> 5
        }
        val range = when (difficulty) {
            Difficulty.EASY -> 1..50
            Difficulty.MEDIUM -> 1..200
            Difficulty.HARD -> 100..1000
        }
        
        val numbers = List(count) { Random.nextInt(range.first, range.last) }.distinct()
        if (numbers.size < count) return generateComparisonTask(difficulty) // Retry if collision

        val findLargest = Random.nextBoolean()
        val question = if (findLargest) "Welche Zahl ist die GRÖSSTE?" else "Welche Zahl ist die KLEINSTE?"
        val answer = if (findLargest) numbers.maxOrNull() else numbers.minOrNull()
        
        return GameTask("$question\n${numbers.joinToString(", ")}", answer.toString())
    }

    private fun generateUnitTask(difficulty: Difficulty): GameTask {
        val units = listOf(
            Triple("kg", "g", 1000),
            Triple("m", "cm", 100),
            Triple("km", "m", 1000),
            Triple("L", "ml", 1000)
        )
        val unit = units.random()
        
        val value = when (difficulty) {
            Difficulty.EASY -> Random.nextInt(1, 10)
            Difficulty.MEDIUM -> Random.nextInt(10, 50)
            Difficulty.HARD -> Random.nextInt(50, 500)
        }
        
        val question = "Wie viele ${unit.second} sind ${value} ${unit.first}?"
        val answer = (value * unit.third).toString()
        
        return GameTask(question, answer)
    }
}