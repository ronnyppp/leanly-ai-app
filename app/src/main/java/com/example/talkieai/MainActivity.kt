package com.example.talkieai

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
import com.example.talkieai.models.ScreenModel
import com.example.talkieai.ui.theme.TalkieAITheme

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
    var currentScreen by remember { mutableStateOf<ScreenModel>(ScreenModel.HOME) }

    when (val screen = currentScreen) {
        is ScreenModel.HOME -> HomePage(
            modifier = modifier,
            onNavigate = { currentScreen = it }
        )
        is ScreenModel.CHAT -> ChatPage(
            modifier = modifier,
            viewModel = viewModel,
            initialPrompt = screen.initialPrompt,
            onBackClick = { currentScreen = ScreenModel.HOME }
        )
    }
}
