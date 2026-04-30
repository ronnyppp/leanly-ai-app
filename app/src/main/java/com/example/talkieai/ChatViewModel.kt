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

    // Initialize the Gemini Developer API backend service
// Create a `GenerativeModel` instance with a model that supports your use case
    private val model = Firebase.ai(backend = GenerativeBackend.googleAI())
        .generativeModel("gemini-2.5-flash")

    val messageList by lazy {
        mutableStateListOf<MessageModel>()
    }
    private val chat = model.startChat(
        history = listOf(
            content(role = "model") { text(fitnessPrompt) }
        ))




    fun sendMessage(question: String) {
        viewModelScope.launch {
            try {
                messageList.add(MessageModel(question, "user"))
                // show fake typing from model
                messageList.add(MessageModel("Typing...","model"))

                val response = chat.sendMessage(question)
                val reply = response.text ?: "No response"

                if(messageList.isNotEmpty()) {
                    messageList.removeAt(messageList.lastIndex)
                }

                messageList.add(MessageModel(reply, "model"))
            } catch (e: Exception) {
                if(messageList.isNotEmpty()) {
                    messageList.removeAt(messageList.lastIndex)
                }
                messageList.add(MessageModel("Error: "+e.message.toString(),"model"))
                Log.e("ChatViewModel", "Error sending message", e)
            }
        }
    }
}