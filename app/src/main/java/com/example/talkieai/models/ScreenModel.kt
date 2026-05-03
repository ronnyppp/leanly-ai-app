package com.example.talkieai.models

sealed class ScreenModel(val route: String) {
    object HOME : ScreenModel("home")

    object SAVED_CHATS : ScreenModel("saved_chats")

    object CHAT : ScreenModel("chat/{chatId}") {
        fun createRoute(chatId: String) = "chat/$chatId"
    }
}
