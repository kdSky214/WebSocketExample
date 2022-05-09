package com.kd.example.websocket.network

import com.google.gson.Gson
import com.kd.example.websocket.network.model.WSData
import okhttp3.*
import okio.ByteString
import timber.log.Timber

class WebSocketService(private val httpClient:OkHttpClient) {


    companion object{
        lateinit var INSTANCE:WebSocketService

        const val SCHEME = "http"
        const val HOST = "192.168.0.101"
        const val PORT = 20336
        var isConnect:Boolean = false
    }
    var ws : WebSocket? = null
    var callback : WebSocketServiceCallback? = null

    init {
        INSTANCE = this
    }

    private fun createHttpUrl() = HttpUrl.Builder().run {
        scheme(SCHEME)
        host(HOST)
        port(PORT)
//        addPathSegment(PATH_SEGMENT)
        build()
    }

    fun connect(){
        if(!isConnect){
            httpClient.newWebSocket(Request.Builder().url(createHttpUrl()).build(), wsListener)
        }
    }

    fun disconnect(){
        ws?.close(1000, "WebSocket close!!(Bye!!)")
        ws = null
    }

    fun reConnect(){

    }

    fun sendData(wsData: WSData){
        ws?.send(Gson().toJson(wsData))
    }

    fun sendMessage(msg:String){
        Timber.e("send message :${msg}")
        ws?.send("message!!! To Client : ${msg}")
    }

    private val wsListener:WebSocketListener = object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
            ws = webSocket
            Timber.e("onOpen() : ${response}")
            isConnect = true
            callback?.connectSuccess()
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            Timber.e("onMessage()[text] : %s", text)
            try{
                callback?.receivedMessage(Gson().getAdapter(WSData::class.java).fromJson(text))
            }catch (e: Exception){
                Timber.d(e, "[onMessage] Gson Error ${e}")
            }
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            super.onMessage(webSocket, bytes)
            Timber.e("WebSocket onMessage(byte) : $bytes")
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosed(webSocket, code, reason)
            isConnect = false
            Timber.e("onClosed() : %s", code)
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosing(webSocket, code, reason)
            Timber.d("onClosing() : %s", code)
            ws?.close(1000, "Bye!")
            ws = null
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
            Timber.d("onFailure() : %s", t)
            isConnect = false

            ws?.close(1000, "Bye!")
            ws = null
            if (response != null) Timber.d(t, "onFailure() : %s", response.toString())
        }
    }
}

