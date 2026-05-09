package com.example.talkieai.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.talkieai.models.StreakEntity
import com.example.talkieai.models.WeightEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface LeanlyDao {
    // funs for weight tracking
    @Insert
    suspend fun insertWeightEntry(entry: WeightEntry)

    @Query("SELECT * FROM weight_entries ORDER BY `date` DESC")
    fun getAllWeightEntries(): Flow<List<WeightEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStreakEntry(entry: StreakEntity)

    @Query("SELECT * FROM streak ORDER BY lastActiveDate DESC LIMIT 1")
    fun getAllStreakEntries(): Flow<StreakEntity?>
}
