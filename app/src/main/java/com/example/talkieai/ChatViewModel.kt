package com.example.talkieai

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val generativeModel = Firebase.ai(backend = GenerativeBackend.googleAI())
        .generativeModel(
            modelName = "gemini-2.5-flash",
            systemInstruction = content { text(fitnessPrompt) }
        )

    private var chat = generativeModel.startChat()

    val messageList = mutableStateListOf<MessageModel>()
    
    private var lastInitialPrompt: String? = null

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
                messageList.add(MessageModel(question, "user"))
                // Show typing placeholder
                messageList.add(MessageModel("Typing...", "model"))

                val response = chat.sendMessage(question)
                val reply = response.text ?: "No response"

                if (messageList.isNotEmpty() && messageList.last().message == "Typing...") {
                    messageList.removeAt(messageList.lastIndex)
                }

                messageList.add(MessageModel(reply, "model"))
            } catch (e: Exception) {
                if (messageList.isNotEmpty() && messageList.last().message == "Typing...") {
                    messageList.removeAt(messageList.lastIndex)
                }
                messageList.add(MessageModel("Error: ${e.localizedMessage}", "model"))
                Log.e("ChatViewModel", "Error sending message", e)
            }
        }
    }
}
