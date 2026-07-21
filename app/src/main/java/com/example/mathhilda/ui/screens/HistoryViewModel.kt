package com.example.mathhilda.ui.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.mathhilda.data.AppDatabase
import com.example.mathhilda.model.GameResult
import kotlinx.coroutines.flow.Flow

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    val history: Flow<List<GameResult>> = db.historyDao().getAllResults()
}