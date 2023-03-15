package com.dicedev.mytestwebsocketapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dicedev.mytestwebsocketapp.ui.theme.MyTestWebsocketAppTheme
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val client = OkHttpClient.Builder().readTimeout(3, TimeUnit.SECONDS).build()
        val request = Request.Builder().url("wss://s8611.nyc1.piesocket.com/v3/1?api_key=PxVnFmjTAaNKQOaIRmEwLkjRrBiudCggeFdZXDYV&notify_self=1").build()

        val viewModel = MainViewModel()
        val wsListener = EchoWebSocketListener(viewModel)
        val webSocket = client.newWebSocket(request, wsListener)
        setContent {
            MyTestWebsocketAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen(viewModel, webSocket)
                }
            }
        }
    }
}

@Composable
fun ChatApp(webSocket: WebSocket) {
    val messageText = remember {
        mutableStateOf("")
    }
    Column(verticalArrangement = Arrangement.Bottom, modifier = Modifier.padding(20.dp)) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = messageText.value,
                onValueChange = { messageText.value = it },
                placeholder = { Text(text = "Message") },
                modifier = Modifier.fillMaxWidth(.85f)
            )
            IconButton(onClick = {
                sendMessage(messageText.value, webSocket)
            }) {
                Icon(imageVector = Icons.Default.Send, contentDescription = "Send message icon")
            }
        }
    }
}

fun sendMessage(message: String, webSocket: WebSocket) {
    webSocket.send(message)
}
