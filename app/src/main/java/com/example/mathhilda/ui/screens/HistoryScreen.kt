package com.example.mathhilda.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mathhilda.model.GameResult
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel,
    onBack: () -> Unit
) {
    val results by viewModel.history.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Spielhistorie") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Zurück")
                    }
                }
            )
        }
    ) { innerPadding ->
        if (results.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                Text("Noch keine Spiele absolviert.")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(results) { result ->
                    HistoryItem(result)
                }
            }
        }
    }
}

@Composable
fun HistoryItem(result: GameResult) {
    val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    val dateStr = sdf.format(Date(result.timestamp))

    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = result.gameType, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = "Schwierigkeit: ${result.difficulty}", fontSize = 12.sp)
                Text(text = dateStr, fontSize = 12.sp, color = MaterialTheme.colorScheme.secondary)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(text = "${result.score} Pkt", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Text(text = "${result.correctAnswers} R / ${result.incorrectAnswers} F", fontSize = 12.sp)
            }
        }
    }
}