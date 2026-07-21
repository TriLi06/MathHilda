package com.example.mathhilda.logic

import com.example.mathhilda.model.Difficulty
import com.example.mathhilda.model.GameEngine
import com.example.mathhilda.model.GameTask
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.random.Random

class DateEngine : GameEngine {
    override fun generateTask(difficulty: Difficulty): GameTask {
        val today = LocalDate.now()
        
        val (startDate, endDate) = when (difficulty) {
            Difficulty.EASY -> {
                val start = today.withDayOfMonth(Random.nextInt(1, 15))
                val end = start.plusDays(Random.nextLong(1, 15))
                start to end
            }
            Difficulty.MEDIUM -> {
                val start = today.withDayOfMonth(Random.nextInt(1, 28))
                val end = start.plusDays(Random.nextLong(15, 60))
                start to end
            }
            Difficulty.HARD -> {
                val start = today.minusMonths(Random.nextLong(1, 6))
                val end = start.plusDays(Random.nextLong(60, 365))
                start to end
            }
        }

        val daysBetween = ChronoUnit.DAYS.between(startDate, endDate)
        
        val question = "Wie viele Tage liegen zwischen dem ${startDate.dayOfMonth}.${startDate.monthValue}. und dem ${endDate.dayOfMonth}.${endDate.monthValue}.?"
        return GameTask(question, daysBetween.toString())
    }
}