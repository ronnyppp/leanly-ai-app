package com.example.talkieai.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.talkieai.models.WeightEntry

@Composable
fun ProgressCard(
    calories: Int = 1800,
    calorieGoal: Int = 2200,
    steps: Int = 6200,
    workoutsDone: Int = 1,
    workoutGoal: Int = 4,
    weights: List<WeightEntry>
) {
    val currWeight = weights.firstOrNull()?.weight
    val prevWeight = weights.drop(1).firstOrNull()?.weight

    val weightChange = if (currWeight != null && prevWeight != null) {
        currWeight - prevWeight
    } else {
        null
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {

        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "\uD83D\uDCAA Today’s Progress",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // LEFT: Weight
                Column {
                    Text("⚖️ Weight", color = Color.Black)
                    Text(
                        text = currWeight?.let { "$it lbs" } ?: "--",
                        color = Color.Black,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                // RIGHT: Stats
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        "Calories: $calories / $calorieGoal",
                        color = Color.Black
                    )
                    Text(
                        "Workouts: $workoutsDone / $workoutGoal",
                        color = Color.Black
                    )
                    Text(
                        "Steps: $steps",
                        color = Color.Black
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            weightChange?.let {
                Text(
                    text = if (it >= 0)
                        "+${"%.1f".format(it)} lbs"
                    else
                        "${"%.1f".format(it)} lbs",
                    color = if (it >= 0)
                        MaterialTheme.colorScheme.error
                    else
                        Color(0xFF00C853)
                )
            }
        }
    }
}