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
        containerColor = Color.White,
        onDismissRequest = onDismiss,
        title = { Text("Add Weight", color = Color.Black) },
        text = {
            TextField(
                value = input,
                onValueChange = { input = it }, // MUST be String
                placeholder = { Text("Enter weight (lbs)", color = Color.Black) },
                colors = androidx.compose.material3.TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,

                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,

                    cursorColor = Color.Black
                )
            )
        },
        confirmButton = {
            Button(onClick = {
                input.toFloatOrNull()?.let {
                    onConfirm(it)
                    input = ""
                    onDismiss()
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