package com.dicedev.mytestwebsocketapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _state = mutableStateOf(MainScreenState())
    val state: State<MainScreenState> = _state

    fun addMessage(message: String) = viewModelScope.launch(Dispatchers.Main) {
        val currentMessageList = mutableListOf<String>()
        currentMessageList.addAll(_state.value.messages)
        currentMessageList.add(message)
        _state.value = state.value.copy(messages = currentMessageList)

    }

    fun setStatus(status: Boolean) = viewModelScope.launch(Dispatchers.Main) {
        _state.value = _state.value.copy(status = false)
    }
}