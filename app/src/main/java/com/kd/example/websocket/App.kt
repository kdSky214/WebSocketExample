package com.kd.example.websocket

import android.app.Application
import com.kd.example.websocket.di.NetworkModule
import com.kd.example.websocket.di.RepositoryModule
import com.kd.example.websocket.di.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    companion object{
        const val retrofitUrl = "http://192.168.0.101:20333"
    }
    override fun onCreate() {
        super.onCreate()

        createKoin()
    }


    private fun initTimber(){
        Timber.plant(Timber.DebugTree())
        Timber.tag("[WebSocket TEST]")
    }

    private fun createKoin(){
        startKoin {
            androidLogger()
            androidContext(this@App)
            koin.loadModules(listOf(NetworkModule, RepositoryModule, ViewModelModule))
        }
    }

}