package com.example.mathhilda.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NumberPad(
    onNumberClick: (String) -> Unit,
    onDeleteClick: () -> Unit,
    onSubmitClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        val buttons = listOf(
            listOf("1", "2", "3"),
            listOf("4", "5", "6"),
            listOf("7", "8", "9"),
            listOf("0")
        )

        buttons.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                row.forEach { digit ->
                    Button(
                        onClick = { onNumberClick(digit) },
                        modifier = Modifier.weight(1f).height(64.dp),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(text = digit, fontSize = 24.sp)
                    }
                }
                
                if (row.size == 1) { // Special case for row with "0"
                    IconButton(
                        onClick = onDeleteClick,
                        modifier = Modifier.weight(1f).height(64.dp),
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Icon(Icons.Default.Backspace, contentDescription = "Delete")
                    }
                    Button(
                        onClick = onSubmitClick,
                        modifier = Modifier.weight(1f).height(64.dp),
                        shape = MaterialTheme.shapes.medium,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    ) {
                        Text(text = "OK", fontSize = 20.sp)
                    }
                }
            }
        }
    }
}