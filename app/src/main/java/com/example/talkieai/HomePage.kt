package com.example.talkieai

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.talkieai.models.ScreenModel
import com.example.talkieai.widgets.AppHeader
import com.example.talkieai.widgets.HomeActionCard
import com.example.talkieai.widgets.StreakCard

@Composable
fun HomePage(modifier: Modifier = Modifier,
             streak: Int = 10,
             workoutOfDay: String? = "",
             onNavigate: (ScreenModel) -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        AppHeader()
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(
                    "Welcome back 👋",
                    fontSize = 25.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    "April 19, 2026",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            StreakCard(streak)
        }
        Spacer(modifier = Modifier.height(12.dp))

        HomeActionCard(
            "Chat with LeanlyAI",
            "Ask me anything about fitness",
            onClick = {onNavigate(ScreenModel.CHAT)}
        )
        HomeActionCard(
            "Generate Workout",
            "LeanlyAI generates a custom workout",
            onClick = {onNavigate(ScreenModel.WORKOUT)}
        )
        HomeActionCard(
            "Meal Suggestion",
            "Quick Nutrition Ideas",
            onClick = {onNavigate(ScreenModel.NUTRITION)}
        )
        Spacer(modifier = Modifier.height(12.dp))
        workoutOfDay?.let {
            Text("Workout of the Day")

            Spacer(modifier = Modifier.height(8.dp))

            Card {
                Text(it, modifier = Modifier.padding(16.dp),
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.surface)
            }
        }
    }
}