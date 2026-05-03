package com.example.talkieai.models

import java.util.UUID

data class ChatConversation(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "New Chat",
    val messages: List<ChatMessage>,
    val timestamp: Long = System.currentTimeMillis()
)

enum class Role {
    USER,
    AI
}
data class ChatMessage(
    val role: Role,
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)