package com.example.talkieai.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.talkieai.models.StreakEntity
import com.example.talkieai.models.WeightEntry

@Database(
    entities = [WeightEntry::class, StreakEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): LeanlyDao
}
