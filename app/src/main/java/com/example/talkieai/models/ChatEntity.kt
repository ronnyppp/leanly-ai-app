package com.example.talkieai.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson

@Entity(tableName = "chats")
data class ChatEntity(
    @PrimaryKey val id: String,
    val title: String,
    val messagesJson: String,
    val timestamp: Long
)

fun ChatEntity.toChatConversation(): ChatConversation {
    // parse messagesJson to List<ChatMessage>
    val messages = try {
        if (messagesJson.isBlank()) {
            emptyList<ChatMessage>()
        } else {
            Gson().fromJson(messagesJson, Array<ChatMessage>::class.java)?.toList() ?: emptyList()
        }
    } catch (e: Exception) {
        emptyList<ChatMessage>()
    }

    return ChatConversation(
        id = id,
        title = title,
        messages = messages,
        timestamp = timestamp
    )
}
