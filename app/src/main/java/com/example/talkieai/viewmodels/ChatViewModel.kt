package com.example.talkieai.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.talkieai.models.ChatConversation
import com.example.talkieai.models.ChatMessage
import com.example.talkieai.models.Role
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.ai.type.content
import kotlinx.coroutines.launch
import java.util.UUID

class ChatViewModel : ViewModel() {
    private val fitnessPrompt = """
You are a professional fitness coach AI.

You help users with:
- workout plans
- nutrition advice
- fitness motivation

Rules:
- Always be structured
- Use bullet points for workouts
- Ask clarifying questions when needed
""".trimIndent()

    private val generativeModel = Firebase.ai(backend = GenerativeBackend.Companion.googleAI())
        .generativeModel(
            modelName = "gemini-2.5-flash",
            systemInstruction = content { text(fitnessPrompt) }
        )

    private var chat = generativeModel.startChat()

    var chats by mutableStateOf<Map<String, ChatConversation>>(emptyMap())
        private set

    var currChatId by mutableStateOf<String?>(null)
        private set

    // UI state only
    private val loadingChats = mutableStateMapOf<String, Boolean>()


    fun createChat(prompt: String): String {
        val chatId = UUID.randomUUID().toString()


        val newChat = ChatConversation(
            id = chatId,
            title = if (prompt.isNotBlank()) prompt.take(40) else "New Chat",
            messages = emptyList(),
            timestamp = System.currentTimeMillis()
        )

        chats = chats + (chatId to newChat)
        currChatId = chatId

        // reset Gemini session
        chat = generativeModel.startChat()

        // if prompt exists → send as first message
        if (prompt.isNotBlank()) {
            sendMessage(chatId, prompt)
        }

        return chatId
    }

    fun getMessages(chatId: String): List<ChatMessage> {
        return chats[chatId]?.messages ?: emptyList()
    }


    fun sendMessage(chatId: String, question: String) {
        viewModelScope.launch {

            val chatConvo = chats[chatId] ?: return@launch

            loadingChats[chatId] = true

            try {
                // add user message
                val updated = chatConvo.messages + ChatMessage(
                    role = Role.USER,
                    content = question,
                    timestamp = System.currentTimeMillis()
                )

                chats = chats + (chatId to chatConvo.copy(messages = updated))

                // AI response
                val response = chat.sendMessage(question)
                val reply = response.text ?: "No response"

                val finalMessages = chats[chatId]!!.messages + ChatMessage(
                    role = Role.AI,
                    content = reply,
                    timestamp = System.currentTimeMillis()
                )

                chats = chats + (chatId to chats[chatId]!!.copy(messages = finalMessages))

            } catch (e: Exception) {

                val errorMessages = chats[chatId]!!.messages + ChatMessage(
                    role = Role.AI,
                    content = "Error: ${e.localizedMessage}",
                    timestamp = System.currentTimeMillis()
                )

                chats = chats + (chatId to chats[chatId]!!.copy(messages = errorMessages))

                Log.e("ChatViewModel", "Error sending message", e)

            } finally {
                loadingChats[chatId] = false
            }
        }
    }

    var savedChats by mutableStateOf<List<ChatConversation>>(emptyList())
        private set

    fun saveConversation(chatId: String) {
        val chat = chats[chatId] ?: return
        savedChats = savedChats + chat
    }

    fun isLoading(chatId: String): Boolean {
        return loadingChats[chatId] == true
    }
}