package com.kd.example.websocket.activity

import android.os.Bundle
import com.kd.example.websocket.R
import com.kd.example.websocket.activity.base.BaseActivity
import com.kd.example.websocket.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val viewModel: MainViewModel by viewModel()

    override val layoutResourceID: Int
        get() = R.layout.activity_main

    override fun initUI() {
        binding.btnMessage.setOnClickListener{
            Timber.e("btnMessage onClick")
            viewModel.socketSendMessage()
        }
        binding.btnData.setOnClickListener {
            Timber.e("btnData onClick")
            viewModel.socketSendData()
        }
    }

    override fun initObservers() {
        viewModel.socketConnect()
    }

    override fun onDestroy() {
        viewModel.socketDisconnect()
        super.onDestroy()
    }
}