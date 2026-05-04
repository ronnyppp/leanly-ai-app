package com.example.talkieai.`data`.local

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.example.talkieai.models.StreakEntity
import com.example.talkieai.models.WeightEntry
import javax.`annotation`.processing.Generated
import kotlin.Float
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class LeanlyDao_Impl(
  __db: RoomDatabase,
) : LeanlyDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfWeightEntry: EntityInsertAdapter<WeightEntry>

  private val __insertAdapterOfStreakEntity: EntityInsertAdapter<StreakEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfWeightEntry = object : EntityInsertAdapter<WeightEntry>() {
      protected override fun createQuery(): String =
          "INSERT OR ABORT INTO `weight_entries` (`id`,`weight`,`date`) VALUES (nullif(?, 0),?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: WeightEntry) {
        statement.bindLong(1, entity.id.toLong())
        statement.bindDouble(2, entity.weight.toDouble())
        statement.bindText(3, entity.date)
      }
    }
    this.__insertAdapterOfStreakEntity = object : EntityInsertAdapter<StreakEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `streak` (`id`,`currentStreak`,`lastActiveDate`) VALUES (?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: StreakEntity) {
        statement.bindLong(1, entity.id.toLong())
        statement.bindLong(2, entity.currentStreak.toLong())
        statement.bindLong(3, entity.lastActiveDate)
      }
    }
  }

  public override suspend fun insertWeightEntry(entry: WeightEntry): Unit = performSuspending(__db,
      false, true) { _connection ->
    __insertAdapterOfWeightEntry.insert(_connection, entry)
  }

  public override suspend fun insertStreakEntry(entry: StreakEntity): Unit = performSuspending(__db,
      false, true) { _connection ->
    __insertAdapterOfStreakEntity.insert(_connection, entry)
  }

  public override fun getAllWeightEntries(): Flow<List<WeightEntry>> {
    val _sql: String = "SELECT * FROM weight_entries ORDER BY `date` DESC"
    return createFlow(__db, false, arrayOf("weight_entries")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfWeight: Int = getColumnIndexOrThrow(_stmt, "weight")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _result: MutableList<WeightEntry> = mutableListOf()
        while (_stmt.step()) {
          val _item: WeightEntry
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpWeight: Float
          _tmpWeight = _stmt.getDouble(_columnIndexOfWeight).toFloat()
          val _tmpDate: String
          _tmpDate = _stmt.getText(_columnIndexOfDate)
          _item = WeightEntry(_tmpId,_tmpWeight,_tmpDate)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAllStreakEntries(): Flow<StreakEntity?> {
    val _sql: String = "SELECT * FROM streak ORDER BY lastActiveDate DESC LIMIT 1"
    return createFlow(__db, false, arrayOf("streak")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfCurrentStreak: Int = getColumnIndexOrThrow(_stmt, "currentStreak")
        val _columnIndexOfLastActiveDate: Int = getColumnIndexOrThrow(_stmt, "lastActiveDate")
        val _result: StreakEntity?
        if (_stmt.step()) {
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpCurrentStreak: Int
          _tmpCurrentStreak = _stmt.getLong(_columnIndexOfCurrentStreak).toInt()
          val _tmpLastActiveDate: Long
          _tmpLastActiveDate = _stmt.getLong(_columnIndexOfLastActiveDate)
          _result = StreakEntity(_tmpId,_tmpCurrentStreak,_tmpLastActiveDate)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
