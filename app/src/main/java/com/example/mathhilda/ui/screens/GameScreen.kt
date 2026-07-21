package com.example.mathhilda.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mathhilda.ui.components.AnimalFeedback
import com.example.mathhilda.ui.components.GameHeader
import com.example.mathhilda.ui.components.NumberPad

@Composable
fun GameScreen(
    viewModel: GameViewModel,
    onBack: () -> Unit
) {
    if (viewModel.isGameOver) {
        ResultView(viewModel, onBack)
    } else {
        GameView(viewModel)
    }
}

@Composable
fun GameView(viewModel: GameViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GameHeader(
            timerSeconds = viewModel.timerSeconds,
            score = viewModel.score,
            correctCount = viewModel.correctCount
        )

        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.shapes.large),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(16.dp)) {
                Text(
                    text = viewModel.currentTask?.question ?: "Lädt...",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    lineHeight = 36.sp
                )
                if (viewModel.currentTask?.displayInfo?.isNotEmpty() == true) {
                    Text(
                        text = viewModel.currentTask!!.displayInfo,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    text = viewModel.userInput,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.small)
                        .padding(horizontal = 24.dp, vertical = 8.dp)
                        .widthIn(min = 100.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        NumberPad(
            onNumberClick = { viewModel.onNumberInput(it) },
            onDeleteClick = { viewModel.onDelete() },
            onSubmitClick = { viewModel.onSubmit() }
        )
    }
}

@Composable
fun ResultView(viewModel: GameViewModel, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Spiel Vorbei!", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        
        Spacer(modifier = Modifier.height(16.dp))
        
        AnimalFeedback(
            animal = viewModel.feedbackAnimal,
            message = viewModel.motivationalMessage
        )

        Spacer(modifier = Modifier.height(24.dp))
        
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Deine Punkte: ${viewModel.score}", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text(text = "Richtig: ${viewModel.correctCount}", color = MaterialTheme.colorScheme.primary)
                Text(text = "Falsch: ${viewModel.incorrectCount}", color = MaterialTheme.colorScheme.error)
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Zurück zum Hauptmenü")
        }
    }
}