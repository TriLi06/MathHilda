package com.example.mathhilda.data

import androidx.room.TypeConverter
import com.example.mathhilda.model.Difficulty

class Converters {
    @TypeConverter
    fun fromDifficulty(value: Difficulty): String {
        return value.name
    }

    @TypeConverter
    fun toDifficulty(value: String): Difficulty {
        return Difficulty.valueOf(value)
    }
}