package com.example.talkieai.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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

    val messageList = mutableStateListOf<ChatMessage>()

    private var lastInitialPrompt: String? = null

    var savedChats by mutableStateOf(listOf<ChatConversation>())
        private set

    fun saveConversation(title: String, messages: List<ChatMessage>) {
        val newChat = ChatConversation(
            title = title,
            messages = messages
        )
        savedChats = savedChats + newChat
    }
    fun sendInitialPrompt(prompt: String) {
        if (prompt.isBlank() || prompt == lastInitialPrompt) return

        lastInitialPrompt = prompt
        // Clear previous conversation and restart chat session for a fresh context
        messageList.clear()
        chat = generativeModel.startChat()

        sendMessage(prompt)
    }

    fun sendMessage(question: String) {
        viewModelScope.launch {
            try {
                messageList.add(ChatMessage(Role.USER,question, System.currentTimeMillis()))
                // Show typing placeholder
                messageList.add(ChatMessage(Role.AI,"Typing...", System.currentTimeMillis()))

                val response = chat.sendMessage(question)
                val reply = response.text ?: "No response"

                if (messageList.isNotEmpty() && messageList.last().content == "Typing...") {
                    messageList.removeAt(messageList.lastIndex)
                }

                messageList.add(ChatMessage(Role.AI,reply, System.currentTimeMillis()))
            } catch (e: Exception) {
                if (messageList.isNotEmpty() && messageList.last().content == "Typing...") {
                    messageList.removeAt(messageList.lastIndex)
                }
                messageList.add(ChatMessage(Role.AI,"Error: ${e.localizedMessage}", System.currentTimeMillis()))
                Log.e("ChatViewModel", "Error sending message", e)
            }
        }
    }
}