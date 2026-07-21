package com.example.mathhilda.logic

import com.example.mathhilda.model.Difficulty
import com.example.mathhilda.model.GameEngine
import com.example.mathhilda.model.GameTask
import kotlin.random.Random

class PatternEngine : GameEngine {
    override fun generateTask(difficulty: Difficulty): GameTask {
        return when (difficulty) {
            Difficulty.EASY -> generateArithmeticPattern(1, 5, 2, 5)
            Difficulty.MEDIUM -> {
                if (Random.nextBoolean()) generateArithmeticPattern(5, 20, 3, 10)
                else generateGeometricPattern(2, 2, 4)
            }
            Difficulty.HARD -> {
                val r = Random.nextInt(3)
                when (r) {
                    0 -> generateArithmeticPattern(10, 50, -5, 10)
                    1 -> generateGeometricPattern(3, 2, 4)
                    else -> generateSquarePattern()
                }
            }
        }
    }

    private fun generateArithmeticPattern(startMin: Int, startMax: Int, stepMax: Int, length: Int): GameTask {
        val start = Random.nextInt(startMin, startMax)
        val step = if (stepMax > 0) Random.nextInt(1, stepMax + 1) else Random.nextInt(stepMax, 0)
        
        val sequence = mutableListOf<Int>()
        for (i in 0 until length) {
            sequence.add(start + i * step)
        }
        
        val question = sequence.take(length - 1).joinToString(", ") + ", ?"
        return GameTask("Setze die Folge fort:\n$question", sequence.last().toString())
    }

    private fun generateGeometricPattern(start: Int, factor: Int, length: Int): GameTask {
        val sequence = mutableListOf<Int>()
        var current = start
        for (i in 0 until length) {
            sequence.add(current)
            current *= factor
        }
        
        val question = sequence.take(length - 1).joinToString(", ") + ", ?"
        return GameTask("Setze die Folge fort:\n$question", sequence.last().toString())
    }

    private fun generateSquarePattern(): GameTask {
        val start = Random.nextInt(1, 6)
        val sequence = mutableListOf<Int>()
        for (i in 0 until 4) {
            val n = start + i
            sequence.add(n * n)
        }
        val question = sequence.take(3).joinToString(", ") + ", ?"
        return GameTask("Setze die Folge fort:\n$question", sequence.last().toString())
    }
}