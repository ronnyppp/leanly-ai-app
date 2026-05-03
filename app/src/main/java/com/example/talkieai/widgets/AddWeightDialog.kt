package com.example.talkieai.widgets

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

@Composable
fun AddWeightDialog(
    onDismiss: () -> Unit,
    onConfirm: (Float) -> Unit
) {
    var input by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Weight") },
        text = {
            TextField(
                value = input,
                onValueChange = { input = it }, // MUST be String
                placeholder = { Text("Enter weight (lbs)") }
            )
        },
        confirmButton = {
            Button(onClick = {
                input.toFloatOrNull()?.let {
                    onConfirm(it)
                }
            }) {
                Text("Save", color = Color.Black)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = Color.Black)
            }
        }
    )
}