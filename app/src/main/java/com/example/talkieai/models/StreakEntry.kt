package com.example.talkieai.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "streak")
data class StreakEntity(
    @PrimaryKey val id: Int = 0,
    val currentStreak: Int,
    val lastActiveDate: Long
)
