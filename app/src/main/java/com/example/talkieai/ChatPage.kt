package com.example.talkieai


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.talkieai.ui.theme.Purple80
import com.example.talkieai.ui.theme.modelMessageColor
import com.example.talkieai.ui.theme.userMessageColor
import com.example.talkieai.viewmodels.ChatViewModel
import com.example.talkieai.models.MessageModel
import com.example.talkieai.widgets.AppHeader

@Composable
fun ChatPage(modifier: Modifier = Modifier,
             viewModel: ChatViewModel,
             initialPrompt: String = "",
             onBackClick: () -> Unit,
             onSaveChatClick: () -> Unit) {
    LaunchedEffect(initialPrompt) {
        if(initialPrompt.isNotBlank())
            viewModel.sendInitialPrompt(initialPrompt)
    }
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        AppHeader(onBackClick = onBackClick, onSaveChatClick = onSaveChatClick)
        MessageList(
            modifier = Modifier
                .weight(1f)
                .background(MaterialTheme.colorScheme.secondary),
            messageList = viewModel.messageList
        )
        MessageInput(onMessageSend = {
            // send message to view model
            viewModel.sendMessage(it)
        })
    }
}

@Composable
fun MessageInput(onMessageSend : (String)-> Unit, initialText: String = "") {
    var message by remember {
        mutableStateOf("")
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = message,
            onValueChange = {
                message = it
            },
            colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary,
            )
        )
        IconButton(onClick = {
            if(message.isNotEmpty()) {
                // save message
                onMessageSend(message)
                message = ""
            }
        }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = "Send",
                tint = Color.Black
            )
        }
    }
}

@Composable
fun MessageList(modifier: Modifier = Modifier, messageList : List<MessageModel>) {
    if(messageList.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.size(60.dp),
                painter = painterResource(id = R.drawable.baseline_question_answer_24),
                contentDescription = "q&a icon",
                tint = Purple80
            )
            Text(
                "Ask me anything",
                fontSize = 22.sp,
                color = Color.Black
            )
        }
    }else {
        LazyColumn(
            modifier = modifier,
            reverseLayout = true
        ) {
            items(messageList.reversed()) {
                MessageRow(messageModel = it)
            }
        }
    }
}

@Composable
fun MessageRow(messageModel: MessageModel) {
    val isUser = messageModel.role=="user"

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                // Align messages left/right according to user/model
                modifier = Modifier.align(if(isUser) Alignment.BottomEnd else Alignment.BottomStart)
                    .padding(
                        start = if(isUser) 70.dp else 8.dp,
                        end = if(isUser) 8.dp else 70.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
                    .clip(RoundedCornerShape(48f))
                    .background(if(isUser) userMessageColor else modelMessageColor)
                    .padding(16.dp)
            ) {
                SelectionContainer() {
                    Text(text = messageModel.message, fontWeight = FontWeight.W500)
                }
            }
        }
    }
}
