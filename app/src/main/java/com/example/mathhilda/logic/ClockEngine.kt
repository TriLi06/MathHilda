package com.example.mathhilda.logic

import com.example.mathhilda.model.Difficulty
import com.example.mathhilda.model.GameEngine
import com.example.mathhilda.model.GameTask
import java.util.*
import kotlin.random.Random

class ClockEngine : GameEngine {
    override fun generateTask(difficulty: Difficulty): GameTask {
        return when (Random.nextInt(3)) {
            0 -> generateTimeAddition(difficulty)
            1 -> generateTimeDifference(difficulty)
            else -> generateTimeSubtraction(difficulty)
        }
    }

    private fun generateTimeAddition(difficulty: Difficulty): GameTask {
        val startHour = Random.nextInt(0, 24)
        val startMinute = when (difficulty) {
            Difficulty.EASY -> Random.nextInt(0, 2) * 30
            Difficulty.MEDIUM -> Random.nextInt(0, 4) * 15
            Difficulty.HARD -> Random.nextInt(0, 60)
        }

        val addHours = when (difficulty) {
            Difficulty.EASY -> Random.nextInt(1, 5)
            Difficulty.MEDIUM -> Random.nextInt(1, 10)
            Difficulty.HARD -> Random.nextInt(1, 24)
        }
        val addMinutes = when (difficulty) {
            Difficulty.EASY -> 0
            Difficulty.MEDIUM -> Random.nextInt(0, 4) * 15
            Difficulty.HARD -> Random.nextInt(1, 60)
        }

        val totalMinutes = (startHour * 60 + startMinute + addHours * 60 + addMinutes) % (24 * 60)
        val endHour = totalMinutes / 60
        val endMin = totalMinutes % 60

        val question = "Start: %02d:%02d\n+ %d:%02d Std.\nEnde?".format(startHour, startMinute, addHours, addMinutes)
        val answer = "%02d%02d".format(endHour, endMin)
        
        return GameTask(question, answer)
    }

    private fun generateTimeDifference(difficulty: Difficulty): GameTask {
        val startHour = Random.nextInt(0, 12)
        val endHour = startHour + Random.nextInt(1, 6)
        
        val startMin = when (difficulty) {
            Difficulty.EASY -> 0
            Difficulty.MEDIUM -> Random.nextInt(0, 4) * 15
            Difficulty.HARD -> Random.nextInt(0, 60)
        }
        val endMin = when (difficulty) {
            Difficulty.EASY -> 0
            Difficulty.MEDIUM -> Random.nextInt(0, 4) * 15
            Difficulty.HARD -> Random.nextInt(0, 60)
        }

        val diffMinutes = (endHour * 60 + endMin) - (startHour * 60 + startMin)
        
        val question = "Wie viele Minuten liegen zwischen %02d:%02d und %02d:%02d?".format(startHour, startMin, endHour, endMin)
        return GameTask(question, diffMinutes.toString())
    }

    private fun generateTimeSubtraction(difficulty: Difficulty): GameTask {
        val startHour = Random.nextInt(12, 24)
        val startMinute = when (difficulty) {
            Difficulty.EASY -> Random.nextInt(0, 2) * 30
            Difficulty.MEDIUM -> Random.nextInt(0, 4) * 15
            Difficulty.HARD -> Random.nextInt(0, 60)
        }

        val subHours = when (difficulty) {
            Difficulty.EASY -> Random.nextInt(1, 5)
            Difficulty.MEDIUM -> Random.nextInt(1, 10)
            Difficulty.HARD -> Random.nextInt(1, 12)
        }
        val subMinutes = when (difficulty) {
            Difficulty.EASY -> 0
            Difficulty.MEDIUM -> Random.nextInt(0, 4) * 15
            Difficulty.HARD -> Random.nextInt(1, 60)
        }

        var totalMinutes = (startHour * 60 + startMinute - (subHours * 60 + subMinutes))
        if (totalMinutes < 0) totalMinutes += 24 * 60
        
        val endHour = (totalMinutes / 60) % 24
        val endMin = totalMinutes % 60

        val question = "Ende: %02d:%02d\n- %d:%02d Std.\nStart?".format(startHour, startMinute, subHours, subMinutes)
        val answer = "%02d%02d".format(endHour, endMin)
        
        return GameTask(question, answer)
    }
}