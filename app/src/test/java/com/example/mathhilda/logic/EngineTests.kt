package com.example.mathhilda.logic

import com.example.mathhilda.model.Difficulty
import org.junit.Assert.*
import org.junit.Test

class EngineTests {

    @Test
    fun testArithmeticEngine() {
        val engine = ArithmeticEngine()
        for (i in 1..100) {
            val task = engine.generateTask(Difficulty.MEDIUM)
            assertNotNull(task.question)
            assertNotNull(task.correctAnswer)
            assertTrue(task.correctAnswer.toIntOrNull() != null)
        }
    }

    @Test
    fun testShoppingEngine() {
        val engine = ShoppingEngine()
        val task = engine.generateTask(Difficulty.HARD)
        // Hard mode: price 5.00 to 95.00, payment next higher bill
        assertTrue(task.question.contains("Preis:"))
        assertTrue(task.question.contains("Bezahlt:"))
        assertTrue(task.correctAnswer.toInt() >= 0)
    }

    @Test
    fun testClockEngine() {
        val engine = ClockEngine()
        val task = engine.generateTask(Difficulty.EASY)
        // Easy mode answers are often HHMM format
        if (task.correctAnswer.length == 4) {
            val hour = task.correctAnswer.substring(0, 2).toInt()
            val min = task.correctAnswer.substring(2, 4).toInt()
            assertTrue(hour in 0..23)
            assertTrue(min in 0..59)
        }
    }

    @Test
    fun testGeometryEngine() {
        val engine = GeometryEngine()
        val task = engine.generateTask(Difficulty.MEDIUM)
        assertTrue(task.question.isNotEmpty())
        assertTrue(task.correctAnswer.toInt() > 0)
    }

    @Test
    fun testPatternEngine() {
        val engine = PatternEngine()
        val task = engine.generateTask(Difficulty.MEDIUM)
        assertTrue(task.question.contains("Setze die Folge fort"))
        assertNotNull(task.correctAnswer.toIntOrNull())
    }

    @Test
    fun testSortingEngine() {
        val engine = SortingEngine()
        val task = engine.generateTask(Difficulty.MEDIUM)
        assertTrue(task.question.isNotEmpty())
        assertNotNull(task.correctAnswer.toIntOrNull())
    }
}