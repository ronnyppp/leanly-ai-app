package com.example.talkieai

import android.R.attr.type
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.talkieai.models.ScreenModel
import com.example.talkieai.ui.theme.TalkieAITheme
import com.example.talkieai.viewmodels.ChatViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val chatViewModel = ViewModelProvider(this)[ChatViewModel::class.java]
        setContent {
            TalkieAITheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.primary
                ) {innerPadding ->
                    App(modifier = Modifier.padding(innerPadding), chatViewModel)
                }
            }
        }
    }
}


@Composable
fun App(modifier: Modifier = Modifier, viewModel: ChatViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ScreenModel.HOME.route,
        modifier = modifier
    ) {
        composable(ScreenModel.HOME.route) {
            HomePage(
                onNavigateToChat = {
                    prompt ->
                    val chatId = viewModel.createChat(prompt)
                    navController.navigate(ScreenModel.CHAT.createRoute(chatId))
                },
                onOpenSaved = {
                    navController.navigate(ScreenModel.SAVED_CHATS.route)
                }
            )
        }
        composable(
            route = ScreenModel.CHAT.route,
            arguments = listOf(navArgument("chatId") {type = NavType.StringType})
        ) {
            backStackEntry ->

            val chatId = backStackEntry.arguments?.getString("chatId")!!

            ChatPage(
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                chatId = chatId,
                onSaveChatClick = {
                    viewModel.saveConversation(chatId)
                    navController.navigate(ScreenModel.SAVED_CHATS.route)
                }
            )

        }
        composable(ScreenModel.SAVED_CHATS.route) {
            SavedChatsPage(
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                onChatClick = { chat ->
                    navController.navigate(ScreenModel.CHAT.createRoute(chat.id))
                },
            )
        }
    }
}
