package com.example.talkieai

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.talkieai.widgets.AppHeader
import com.example.talkieai.widgets.HomeActionCard
import com.example.talkieai.widgets.ProgressCard
import com.example.talkieai.widgets.StreakCard
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import com.example.talkieai.viewmodels.WeightViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.talkieai.widgets.AddWeightDialog
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun HomePage(modifier: Modifier = Modifier,
             weightViewModel: WeightViewModel = viewModel(),
             onNavigateToChat: (String) -> Unit,
             onOpenSaved: () -> Unit) {
    val currentTime = LocalTime.now()
    val greeting = when (currentTime.hour) {
        in 5..11 -> "Good morning ☀️"
        in 12..17 -> "Good afternoon 💪"
        else -> "Good evening 🌙"
    }
    val currentDate = LocalDate.now()
    val formattedDate = currentDate.format(
        DateTimeFormatter.ofPattern("MMMM d, yyyy")
    )

    val weights by weightViewModel.weights.collectAsState()
    val streakData by weightViewModel.streak.collectAsState()

    var showWeightDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        AppHeader()
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    greeting,
                    fontSize = 25.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    formattedDate,
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            StreakCard(streak = streakData?.currentStreak ?: 0)
        }
        ProgressCard(
            weights = weights,
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            item {
                HomeActionCard(
                    "Chat with LeanlyAI",
                    "Ask me anything about fitness",
                    onClick = { onNavigateToChat("") }
                )
            }
            item {
                HomeActionCard(
                    "Workout Ideas",
                    "Beginner friendly workouts",
                    onClick = { onNavigateToChat("Give me a workout for beginners") }
                )
            }
            item {
                HomeActionCard(
                    "Meal Suggestion",
                    "Quick Nutrition Ideas",
                    onClick = { onNavigateToChat("Give me a meal suggestion") }
                )
            }
            item {
                HomeActionCard(
                    "Progress Tips",
                    "Gain guidance from LeanlyAI",
                    onClick = { onNavigateToChat("Give progress tips") }
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ElevatedButton(
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.Black
                ),
                onClick = { onOpenSaved() }
            ) {
                Text("Saved Chats")
            }

            ElevatedButton(
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.Black
                ),
                onClick = { showWeightDialog = true }
            ) {
                Text("Add Weight")
            }
        }
    }
    if (showWeightDialog) {
        AddWeightDialog(
            onDismiss = {
                showWeightDialog = false
            },
            onConfirm = { weight ->
                weightViewModel.addWeight(weight)
                showWeightDialog = false
            }
        )
    }
}
