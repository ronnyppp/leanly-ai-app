package com.example.talkieai.models

import com.example.talkieai.data.local.ChatDao
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow

class ChatRepository(private val dao: ChatDao) {

    suspend fun saveChat(chat: ChatConversation) {
        dao.insertChat(
            ChatEntity(
                id = chat.id,
                title = chat.title,
                messagesJson = Gson().toJson(chat.messages),
                timestamp = chat.timestamp
            )
        )
    }

    fun getSavedChats(): Flow<List<ChatEntity>> = dao.getAllChats()
}