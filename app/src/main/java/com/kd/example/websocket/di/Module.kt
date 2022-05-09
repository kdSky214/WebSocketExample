package com.kd.example.websocket.di

import com.kd.example.websocket.activity.MainViewModel
import com.kd.example.websocket.network.WebSocketService
import com.kd.example.websocket.repository.WebSocketRepository
import com.kd.example.websocket.repository.WebSocketRepositoryImp
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.net.CookieManager
import java.util.concurrent.TimeUnit
private const val TIME_OUT = 30L
private const val HEADER_NAME_LANGUAGE = "lang"
private const val HEADER_NAME_CONTENT_TYPE = "Content-Type"
private const val HEADER_NAME_ACCESS_TOKEN = "accessToken"
private const val HEADER_VALUE_CONTENT_TYPE = "application/json"

val NetworkModule = module {
    single<WebSocketService> { createWebSocketService(createOkhttpClient()) }
}

fun createOkhttpClient() = OkHttpClient.Builder().apply{
    cookieJar(JavaNetCookieJar(CookieManager()))
    connectTimeout(TIME_OUT, TimeUnit.SECONDS)
    readTimeout(TIME_OUT, TimeUnit.SECONDS)
    retryOnConnectionFailure(true)
    addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    })
//    addInterceptor {
//        it.proceed(
//            it.request()
//                .newBuilder()
//                .header(HEADER_NAME_CONTENT_TYPE, HEADER_VALUE_CONTENT_TYPE)
//                .addHeader(HEADER_NAME_ACCESS_TOKEN, "")
//                .build()
//        )
//    }
}.build()

val RepositoryModule = module {
    single<WebSocketRepository> { createWebSocketRepository(get()) }
}

fun createWebSocketService(okHttpClient: OkHttpClient):WebSocketService{
    WebSocketService(okHttpClient)
    return WebSocketService.INSTANCE
}

fun createWebSocketRepository(
    service:WebSocketService
):WebSocketRepository = WebSocketRepositoryImp(service)


val ViewModelModule = module{ viewModel{MainViewModel(get())} }


