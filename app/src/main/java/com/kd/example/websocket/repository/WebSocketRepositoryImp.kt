package com.kd.example.websocket.repository

import com.kd.example.websocket.network.WebSocketService
import com.kd.example.websocket.network.WebSocketServiceCallback
import com.kd.example.websocket.network.model.WSData
import com.kd.example.websocket.util.CustomResult

class WebSocketRepositoryImp(
    private val service: WebSocketService
    ) : WebSocketRepository{
    override fun connect(callback:WebSocketServiceCallback): CustomResult<String> {
        return try{
            service.callback = callback
            service.connect()
            CustomResult.Success("test")
        }catch (e:Exception){
            CustomResult.Error(e)
        }
    }

    override fun disconnect(): CustomResult<String> {
        return try{
            service.disconnect()
            CustomResult.Success("Disconnect")
        }catch (e:Exception){
            CustomResult.Error(e)
        }
    }

    override fun sendData(data: WSData): CustomResult<String> {
        return try{
            service.sendData(data)
            CustomResult.Success("Disconnect")
        }catch (e:Exception){
            CustomResult.Error(e)
        }
    }

    override fun sendMessage(msg: String): CustomResult<String> {
        return try{
            service.sendMessage(msg)
            CustomResult.Success("Disconnect")
        }catch (e:Exception){
            CustomResult.Error(e)
        }
    }


}