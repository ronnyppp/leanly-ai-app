package com.example.talkieai.`data`.local

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class AppDatabase_Impl : AppDatabase() {
  private val _leanlyDao: Lazy<LeanlyDao> = lazy {
    LeanlyDao_Impl(this)
  }

  private val _chatDao: Lazy<ChatDao> = lazy {
    ChatDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(3,
        "f6296ca592a284ba5f1d9b8c38ecf050", "e2879fc0d22ce454a1220e2afcaec13c") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `weight_entries` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `weight` REAL NOT NULL, `date` INTEGER NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `streak` (`id` INTEGER NOT NULL, `currentStreak` INTEGER NOT NULL, `lastActiveDate` INTEGER NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `chats` (`id` TEXT NOT NULL, `title` TEXT NOT NULL, `messagesJson` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'f6296ca592a284ba5f1d9b8c38ecf050')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `weight_entries`")
        connection.execSQL("DROP TABLE IF EXISTS `streak`")
        connection.execSQL("DROP TABLE IF EXISTS `chats`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection):
          RoomOpenDelegate.ValidationResult {
        val _columnsWeightEntries: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsWeightEntries.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsWeightEntries.put("weight", TableInfo.Column("weight", "REAL", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsWeightEntries.put("date", TableInfo.Column("date", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysWeightEntries: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesWeightEntries: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoWeightEntries: TableInfo = TableInfo("weight_entries", _columnsWeightEntries,
            _foreignKeysWeightEntries, _indicesWeightEntries)
        val _existingWeightEntries: TableInfo = read(connection, "weight_entries")
        if (!_infoWeightEntries.equals(_existingWeightEntries)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |weight_entries(com.example.talkieai.models.WeightEntry).
              | Expected:
              |""".trimMargin() + _infoWeightEntries + """
              |
              | Found:
              |""".trimMargin() + _existingWeightEntries)
        }
        val _columnsStreak: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsStreak.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsStreak.put("currentStreak", TableInfo.Column("currentStreak", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsStreak.put("lastActiveDate", TableInfo.Column("lastActiveDate", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysStreak: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesStreak: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoStreak: TableInfo = TableInfo("streak", _columnsStreak, _foreignKeysStreak,
            _indicesStreak)
        val _existingStreak: TableInfo = read(connection, "streak")
        if (!_infoStreak.equals(_existingStreak)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |streak(com.example.talkieai.models.StreakEntity).
              | Expected:
              |""".trimMargin() + _infoStreak + """
              |
              | Found:
              |""".trimMargin() + _existingStreak)
        }
        val _columnsChats: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsChats.put("id", TableInfo.Column("id", "TEXT", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsChats.put("title", TableInfo.Column("title", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsChats.put("messagesJson", TableInfo.Column("messagesJson", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsChats.put("timestamp", TableInfo.Column("timestamp", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysChats: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesChats: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoChats: TableInfo = TableInfo("chats", _columnsChats, _foreignKeysChats,
            _indicesChats)
        val _existingChats: TableInfo = read(connection, "chats")
        if (!_infoChats.equals(_existingChats)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |chats(com.example.talkieai.models.ChatEntity).
              | Expected:
              |""".trimMargin() + _infoChats + """
              |
              | Found:
              |""".trimMargin() + _existingChats)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "weight_entries", "streak",
        "chats")
  }

  public override fun clearAllTables() {
    super.performClear(false, "weight_entries", "streak", "chats")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(LeanlyDao::class, LeanlyDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(ChatDao::class, ChatDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override
      fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>):
      List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun dao(): LeanlyDao = _leanlyDao.value

  public override fun chatDao(): ChatDao = _chatDao.value
}
