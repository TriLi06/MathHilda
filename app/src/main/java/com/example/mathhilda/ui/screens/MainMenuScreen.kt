package com.example.mathhilda.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mathhilda.model.Difficulty

@Composable
fun MainMenuScreen(
    onStartGame: (String, Difficulty) -> Unit,
    onStartChallenge: (Difficulty) -> Unit,
    onShowHistory: () -> Unit
) {
    var selectedDifficulty by remember { mutableStateOf(Difficulty.MEDIUM) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "🦛",
            fontSize = 80.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = "MathHilda",
            fontSize = 42.sp,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        Text(
            text = "Hallo! Ich bin Hilda. Lass uns Mathe üben!",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Schwierigkeit", fontWeight = FontWeight.Bold)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Difficulty.values().forEach { diff ->
                        FilterChip(
                            selected = selectedDifficulty == diff,
                            onClick = { selectedDifficulty = diff },
                            label = { Text(diff.name) }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Spielmodus wählen", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        val modes = listOf(
            "Arithmetic" to ("Grundrechenarten" to "🧮"),
            "Clock" to ("Uhrzeiten" to "⏰"),
            "Shopping" to ("Einkaufen" to "🛒"),
            "Recipe" to ("Rezepte" to "🧁"),
            "Date" to ("Datum" to "📅"),
            "Geometry" to ("Geometrie" to "📐"),
            "Pattern" to ("Muster" to "🧩"),
            "Sorting" to ("Größen" to "⚖️")
        )

        modes.forEach { (id, data) ->
            val (label, icon) = data
            Button(
                onClick = { onStartGame(id, selectedDifficulty) },
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(icon, fontSize = 20.sp, modifier = Modifier.padding(end = 8.dp))
                    Text(label)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onStartChallenge(selectedDifficulty) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
        ) {
            Text("CHALLENGE (3 Spiele)")
        }

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedButton(
            onClick = onShowHistory,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Historie anzeigen")
        }
    }
}