package com.kd.example.websocket.repository

import com.kd.example.websocket.network.WebSocketServiceCallback
import com.kd.example.websocket.network.model.WSData
import com.kd.example.websocket.util.CustomResult
import okhttp3.WebSocketListener

interface WebSocketRepository {
    fun connect(callback: WebSocketServiceCallback):CustomResult<String>
    fun disconnect():CustomResult<String>
    fun sendData(data:WSData):CustomResult<String>
    fun sendMessage(msg:String):CustomResult<String>
}