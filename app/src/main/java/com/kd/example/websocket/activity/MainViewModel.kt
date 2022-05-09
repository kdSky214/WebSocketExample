package com.kd.example.websocket.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kd.example.websocket.network.WebSocketServiceCallback
import com.kd.example.websocket.network.model.WSBody
import com.kd.example.websocket.network.model.WSData
import com.kd.example.websocket.network.model.WSHeader
import com.kd.example.websocket.repository.WebSocketRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(private val socketRepo:WebSocketRepository) : ViewModel(){
    fun socketConnect(){
        socketRepo.connect(wsCallback)
    }

    fun socketDisconnect(){
        socketRepo.disconnect()
    }

    fun socketSendMessage(){
        viewModelScope.launch{
            socketRepo.sendMessage("client message")
        }
    }

    fun socketSendData(){
        viewModelScope.launch{
            val data = WSData(WSHeader("header"), WSBody(0, "d", null))
            socketRepo.sendData(data)
        }
    }

    private val wsCallback = object:WebSocketServiceCallback{
        override fun receivedMessage(response: WSData) {

        }

        override fun connectSuccess() {
            Timber.e("WebSocket Connect!!!!")
        }
    }
}