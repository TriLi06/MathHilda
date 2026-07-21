package com.example.mathhilda.logic

import com.example.mathhilda.model.Difficulty
import com.example.mathhilda.model.GameEngine
import com.example.mathhilda.model.GameTask
import kotlin.random.Random

class ArithmeticEngine : GameEngine {
    override fun generateTask(difficulty: Difficulty): GameTask {
        return when (Random.nextInt(5)) {
            0 -> generateAddition(difficulty)
            1 -> generateSubtraction(difficulty)
            2 -> generateMultiplication(difficulty)
            3 -> generateDivision(difficulty)
            else -> generateChainTask(difficulty)
        }
    }

    private fun generateAddition(difficulty: Difficulty): GameTask {
        val range = when (difficulty) {
            Difficulty.EASY -> 1..20
            Difficulty.MEDIUM -> 10..100
            Difficulty.HARD -> 50..500
        }
        val a = Random.nextInt(range.first, range.last)
        val b = Random.nextInt(range.first, range.last)
        return GameTask("$a + $b", (a + b).toString())
    }

    private fun generateSubtraction(difficulty: Difficulty): GameTask {
        val range = when (difficulty) {
            Difficulty.EASY -> 1..20
            Difficulty.MEDIUM -> 10..100
            Difficulty.HARD -> 50..500
        }
        val a = Random.nextInt(range.first, range.last)
        val b = Random.nextInt(range.first, a + 1)
        return GameTask("$a - $b", (a - b).toString())
    }

    private fun generateMultiplication(difficulty: Difficulty): GameTask {
        val range = when (difficulty) {
            Difficulty.EASY -> 1..10
            Difficulty.MEDIUM -> 2..20
            Difficulty.HARD -> 5..50
        }
        val a = Random.nextInt(range.first, range.last)
        val b = Random.nextInt(range.first, range.last)
        return GameTask("$a * $b", (a * b).toString())
    }

    private fun generateDivision(difficulty: Difficulty): GameTask {
        val range = when (difficulty) {
            Difficulty.EASY -> 1..10
            Difficulty.MEDIUM -> 2..20
            Difficulty.HARD -> 5..30
        }
        val b = Random.nextInt(range.first, range.last)
        val result = Random.nextInt(range.first, range.last)
        val a = b * result
        return GameTask("$a : $b", result.toString())
    }

    private fun generateChainTask(difficulty: Difficulty): GameTask {
        val range = when (difficulty) {
            Difficulty.EASY -> 1..10
            Difficulty.MEDIUM -> 5..20
            Difficulty.HARD -> 10..50
        }
        val a = Random.nextInt(range.first, range.last)
        val b = Random.nextInt(range.first, range.last)
        val c = Random.nextInt(range.first, range.last)
        
        return if (Random.nextBoolean()) {
            GameTask("$a + $b - $c", (a + b - c).toString())
        } else {
            GameTask("$a - $b + $c", (a - b + c).toString())
        }
    }
}