package com.example.talkieai.viewmodels

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.talkieai.data.local.DatabaseProvider
import com.example.talkieai.models.StreakEntity
import com.example.talkieai.models.WeightEntry
import com.example.talkieai.models.WeightStorage.loadWeights
import com.example.talkieai.models.WeightStorage.saveWeights
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

class WeightViewModel(application: Application) : AndroidViewModel(application){

    private val dao = DatabaseProvider.getDatabase(application).dao()

    val weights = dao.getAllWeightEntries()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val streak = dao.getAllStreakEntries()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    fun addWeight(weight: Float) {
        viewModelScope.launch {
            val now = System.currentTimeMillis()

            dao.insertWeightEntry(
                WeightEntry(weight = weight, date = now.toString())
            )
            updateStreak(now)
        }
    }

    private suspend fun updateStreak(now: Long) {
        val curr = streak.value

        val newStreak = if (curr == null) {
            1
        } else {
            val diffDays = ((now - curr.lastActiveDate) / (1000 * 60 * 60 * 24)).toInt()

            when(diffDays) {
                0 -> curr.currentStreak
                1 -> curr.currentStreak + 1
                else -> 1
            }
            }
        dao.insertStreakEntry(
            StreakEntity(
                currentStreak = newStreak,
                lastActiveDate = now
            )
        )
        }
    }

