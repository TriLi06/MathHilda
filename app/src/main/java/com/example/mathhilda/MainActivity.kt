package com.example.mathhilda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mathhilda.model.Difficulty
import com.example.mathhilda.ui.screens.*
import com.example.mathhilda.ui.theme.MathHildaTheme

sealed class Screen {
    object MainMenu : Screen()
    object Game : Screen()
    object History : Screen()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MathHildaTheme {
                var currentScreen by remember { mutableStateOf<Screen>(Screen.MainMenu) }
                val gameViewModel: GameViewModel = viewModel()
                val historyViewModel: HistoryViewModel = viewModel()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when (currentScreen) {
                        is Screen.MainMenu -> {
                            MainMenuScreen(
                                onStartGame = { type, diff ->
                                    gameViewModel.startGame(type, diff)
                                    currentScreen = Screen.Game
                                },
                                onStartChallenge = { diff ->
                                    gameViewModel.startChallenge(diff)
                                    currentScreen = Screen.Game
                                },
                                onShowHistory = {
                                    currentScreen = Screen.History
                                }
                            )
                        }
                        is Screen.Game -> {
                            GameScreen(
                                viewModel = gameViewModel,
                                onBack = { currentScreen = Screen.MainMenu }
                            )
                            BackHandler {
                                currentScreen = Screen.MainMenu
                            }
                        }
                        is Screen.History -> {
                            HistoryScreen(
                                viewModel = historyViewModel,
                                onBack = { currentScreen = Screen.MainMenu }
                            )
                            BackHandler {
                                currentScreen = Screen.MainMenu
                            }
                        }
                    }
                }
            }
        }
    }
}