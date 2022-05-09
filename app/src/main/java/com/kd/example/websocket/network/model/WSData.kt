package com.kd.example.websocket.network.model

import com.google.gson.annotations.SerializedName

data class WSData(
    @SerializedName("header")
    val head:WSHeader,
    @SerializedName("body")
    val body:WSBody) {
}