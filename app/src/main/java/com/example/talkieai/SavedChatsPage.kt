package com.example.talkieai

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.talkieai.models.ChatConversation
import com.example.talkieai.viewmodels.ChatViewModel
import com.example.talkieai.widgets.AppHeader

@Composable
fun SavedChatsPage(
    viewModel: ChatViewModel,
    onBackClick: () -> Unit,
    onChatClick: (ChatConversation) -> Unit
) {
    // Correctly observing the StateFlow from the ViewModel
    val chats by viewModel.savedChats.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        AppHeader(onBackClick = onBackClick)

        if (chats.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center) {
                Text("No saved conversations.", color = Color.Black)
            }
        } else {
            LazyColumn {
                items(chats) { chat ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable { onChatClick(chat) }
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(chat.title, color = Color.Black)
                            Text(
                                text = chat.messages.lastOrNull()?.content ?: "Empty conversation",
                                maxLines = 1,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}
