package com.example.talkieai.models

sealed class ScreenModel {
    object HOME : ScreenModel()
    data class CHAT(val initialPrompt: String = "") : ScreenModel()
}
