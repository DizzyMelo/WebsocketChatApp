package com.dicedev.mytestwebsocketapp

data class MainScreenState(
    val status: Boolean = false,
    val messages: List<String> = emptyList()
)
