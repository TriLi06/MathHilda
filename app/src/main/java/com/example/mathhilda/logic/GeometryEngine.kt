package com.example.mathhilda.logic

import com.example.mathhilda.model.Difficulty
import com.example.mathhilda.model.GameEngine
import com.example.mathhilda.model.GameTask
import kotlin.random.Random

class GeometryEngine : GameEngine {
    override fun generateTask(difficulty: Difficulty): GameTask {
        return when (Random.nextInt(2)) {
            0 -> generatePropertyTask(difficulty)
            else -> generatePerimeterTask(difficulty)
        }
    }

    private fun generatePropertyTask(difficulty: Difficulty): GameTask {
        val shapes = mutableListOf(
            "Dreieck" to 3,
            "Quadrat" to 4,
            "Rechteck" to 4
        )
        if (difficulty != Difficulty.EASY) {
            shapes.add("Fünfeck" to 5)
            shapes.add("Sechseck" to 6)
        }
        if (difficulty == Difficulty.HARD) {
            shapes.add("Achteck" to 8)
        }

        val shape = shapes.random()
        val type = if (Random.nextBoolean()) "Ecken" else "Seiten"
        
        return GameTask("Wie viele $type hat ein ${shape.first}?", shape.second.toString())
    }

    private fun generatePerimeterTask(difficulty: Difficulty): GameTask {
        return when (difficulty) {
            Difficulty.EASY -> {
                val a = Random.nextInt(2, 10)
                GameTask("Umfang eines Quadrats mit Seitenlänge $a?", (4 * a).toString())
            }
            Difficulty.MEDIUM -> {
                val a = Random.nextInt(3, 15)
                val b = Random.nextInt(3, 15)
                GameTask("Umfang eines Rechtecks mit a=$a und b=$b?", (2 * (a + b)).toString())
            }
            Difficulty.HARD -> {
                val a = Random.nextInt(10, 50)
                val b = Random.nextInt(10, 50)
                GameTask("Umfang eines Rechtecks mit a=$a und b=$b?", (2 * (a + b)).toString())
            }
        }
    }
}