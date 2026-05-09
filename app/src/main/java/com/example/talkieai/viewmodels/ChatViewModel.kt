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
import com.example.talkieai.models.ChatRepository
import com.example.talkieai.models.Role
import com.example.talkieai.models.toChatConversation
import com.google.firebase.Firebase
import com.google.firebase.ai.Chat
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.ai.type.content
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

class ChatViewModel(
    private val repository: ChatRepository
) : ViewModel() {
    // initial prompt
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
You are a concise assistant.
Keep responses under 3-5 sentences.
Be direct and avoid unnecessary explanation.
""".trimIndent()

    private val generativeModel = Firebase.ai(backend = GenerativeBackend.Companion.googleAI())
        .generativeModel(
            modelName = "gemini-2.5-flash",
            systemInstruction = content { text(fitnessPrompt) }
        )

    // store separate sessions per chatId to keep conversation context isolated
    private val sessions = mutableMapOf<String, Chat>()

    // using mutableStateMapOf for better reactivity when messages are added
    var activeChats = mutableStateMapOf<String, ChatConversation>()
        private set

    var currChatId by mutableStateOf<String?>(null)
        private set

    // Observe saved chats from Room using StateFlow
    val savedChats = repository.getSavedChats()
        .map { entities ->
            entities.map { it.toChatConversation() }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun createChat(prompt: String): String {
        val chatId = UUID.randomUUID().toString()

        val newChat = ChatConversation(
            id = chatId,
            title = if (prompt.isNotBlank()) prompt.take(40) else "New Chat",
            messages = emptyList(),
            timestamp = System.currentTimeMillis()
        )

        activeChats[chatId] = newChat
        currChatId = chatId
        
        // Start a fresh Gemini session for this chatId
        sessions[chatId] = generativeModel.startChat()

        if (prompt.isNotBlank()) {
            sendMessage(chatId, prompt)
        }

        return chatId
    }

    fun getMessages(chatId: String): List<ChatMessage> {
        return activeChats[chatId]?.messages ?: emptyList()
    }

    fun sendMessage(chatId: String, question: String) {
        viewModelScope.launch {
            val chatConvo = activeChats[chatId] ?: return@launch
            val session = sessions[chatId] ?: generativeModel.startChat().also { sessions[chatId] = it }

            try {
                // add user message and temporary "Typing..." AI placeholder
                val updatedMessages = chatConvo.messages + 
                    ChatMessage(Role.USER, question, System.currentTimeMillis()) +
                    ChatMessage(Role.AI, "Typing...", System.currentTimeMillis())

                activeChats[chatId] = chatConvo.copy(messages = updatedMessages)

                // request AI response
                val response = session.sendMessage(question)
                val reply = response.text ?: "No response"

                // replace "Typing..." with response
                val currentMessages = activeChats[chatId]?.messages ?: emptyList()
                val finalMessages = (if (currentMessages.lastOrNull()?.content == "Typing...") {
                    currentMessages.dropLast(1)
                } else {
                    currentMessages
                }) + ChatMessage(Role.AI, reply, System.currentTimeMillis())

                activeChats[chatId] = activeChats[chatId]!!.copy(messages = finalMessages)

            } catch (e: Exception) {
                // remove temporary indicator on error and show error message
                val currentMessages = activeChats[chatId]?.messages ?: emptyList()
                val errorMessages = (if (currentMessages.lastOrNull()?.content == "Typing...") {
                    currentMessages.dropLast(1)
                } else {
                    currentMessages
                }) + ChatMessage(Role.AI, "Error: ${e.localizedMessage}", System.currentTimeMillis())
                
                activeChats[chatId] = activeChats[chatId]!!.copy(messages = errorMessages)
                Log.e("ChatViewModel", "Error sending message", e)
            }
        }
    }

    fun saveConversation(chatId: String) {
        val chat = activeChats[chatId] ?: return
        viewModelScope.launch {
            repository.saveChat(chat)
        }
    }
}
