package com.example.mathhilda.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameHeader(
    timerSeconds: Int,
    score: Int,
    correctCount: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Zeit: ${timerSeconds}s",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = if (timerSeconds <= 10) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
            )
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "Punkte: $score",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Richtig: $correctCount",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}