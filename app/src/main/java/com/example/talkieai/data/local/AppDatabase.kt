package com.example.talkieai.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.talkieai.models.StreakEntity
import com.example.talkieai.models.WeightEntry
import com.example.talkieai.models.ChatEntity

@Database(
    entities = [WeightEntry::class,
        StreakEntity::class,
        ChatEntity::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): LeanlyDao
    abstract fun chatDao(): ChatDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration(false)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
