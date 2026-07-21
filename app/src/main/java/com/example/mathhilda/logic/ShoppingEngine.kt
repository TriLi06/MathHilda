package com.example.mathhilda.logic

import com.example.mathhilda.model.Difficulty
import com.example.mathhilda.model.GameEngine
import com.example.mathhilda.model.GameTask
import kotlin.random.Random

class ShoppingEngine : GameEngine {
    override fun generateTask(difficulty: Difficulty): GameTask {
        val bills = listOf(5, 10, 20, 50, 100)
        
        val (price, payment) = when (difficulty) {
            Difficulty.EASY -> {
                val p = Random.nextInt(1, 10) * 1.0
                val pay = bills.first { it > p }
                p to pay.toDouble()
            }
            Difficulty.MEDIUM -> {
                val p = Random.nextInt(10, 450) / 10.0 // e.g. 1.1 to 44.9
                val pay = bills.first { it > p }
                p to pay.toDouble()
            }
            Difficulty.HARD -> {
                val p = Random.nextInt(500, 9500) / 100.0 // e.g. 5.00 to 95.00
                val pay = bills.first { it > p }
                p to pay.toDouble()
            }
        }

        val change = payment - price
        // Format to 2 decimal places and remove dot for the answer if needed, 
        // but the prompt says "buttons for input similar to calculator".
        // I'll expect the answer in cents or as a string like "250" for 2.50
        
        val question = "Preis: %.2f €\nBezahlt: %.2f €\nRückgeld?".format(price, payment)
        val answer = "%.0f".format(change * 100) // Answer in cents for easier input
        
        return GameTask(question, answer, "Gib das Rückgeld in Cent ein.")
    }
}