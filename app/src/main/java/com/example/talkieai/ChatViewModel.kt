package com.example.talkieai

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {


    // Initialize the Gemini Developer API backend service
// Create a `GenerativeModel` instance with a model that supports your use case
    private val model = Firebase.ai(backend = GenerativeBackend.googleAI())
        .generativeModel("gemini-2.5-flash")

    val messageList by lazy {
        mutableStateListOf<MessageModel>()
    }
    private val chat = model.startChat()


    fun sendMessage(question: String) {
        viewModelScope.launch {
            try {
                messageList.add(MessageModel(question, "user"))
                // show fake typing from model
                messageList.add(MessageModel("Typing...","model"))

                val userQuestion = chat.sendMessage(question)
                val reply = userQuestion.text ?: "No response"

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