package com.example.talkieai.`data`.local

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.example.talkieai.models.ChatEntity
import javax.`annotation`.processing.Generated
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
public class ChatDao_Impl(
  __db: RoomDatabase,
) : ChatDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfChatEntity: EntityInsertAdapter<ChatEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfChatEntity = object : EntityInsertAdapter<ChatEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `chats` (`id`,`title`,`messagesJson`,`timestamp`) VALUES (?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ChatEntity) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.title)
        statement.bindText(3, entity.messagesJson)
        statement.bindLong(4, entity.timestamp)
      }
    }
  }

  public override suspend fun insertChat(chat: ChatEntity): Unit = performSuspending(__db, false,
      true) { _connection ->
    __insertAdapterOfChatEntity.insert(_connection, chat)
  }

  public override fun getAllChats(): Flow<List<ChatEntity>> {
    val _sql: String = "SELECT * FROM chats ORDER BY timestamp DESC"
    return createFlow(__db, false, arrayOf("chats")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfMessagesJson: Int = getColumnIndexOrThrow(_stmt, "messagesJson")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _result: MutableList<ChatEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: ChatEntity
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpMessagesJson: String
          _tmpMessagesJson = _stmt.getText(_columnIndexOfMessagesJson)
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          _item = ChatEntity(_tmpId,_tmpTitle,_tmpMessagesJson,_tmpTimestamp)
          _result.add(_item)
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
