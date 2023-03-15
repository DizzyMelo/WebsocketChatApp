package com.dicedev.mytestwebsocketapp

import android.util.Log
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class EchoWebSocketListener(private val viewModel: MainViewModel) : WebSocketListener() {
    override fun onOpen(webSocket: WebSocket, response: Response) {
        webSocket.send("Hi")
        webSocket.send("Hoq are you doing from the app?")
        viewModel.setStatus(status = true)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        output("Receiving: $text")
        viewModel.addMessage(text)
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        output("Receiving: ${bytes.hex()}")
        viewModel.addMessage(bytes.hex())
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        webSocket.close(NORMAL_CLOSURE_STATUS, null)
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        viewModel.setStatus(false)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        output("Error: ${t.message}")
    }

    companion object {
        private const val NORMAL_CLOSURE_STATUS = 1000
    }

    private fun output(txt: String) {
        Log.d("WSS", txt)
    }
}