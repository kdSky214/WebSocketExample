package com.kd.example.websocket.activity.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

abstract class BaseActivity<T: ViewDataBinding> : AppCompatActivity(), CoroutineScope {
    protected lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    lateinit var binding:T
    abstract val layoutResourceID:Int
    abstract fun initUI()
    abstract fun initObservers()
    override fun onCreate(savedInstanceState: Bundle?) {
        job = SupervisorJob()
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResourceID)
        binding.lifecycleOwner = this
        initUI()
        initObservers()
    }

    override fun onDestroy() {
        release()
        super.onDestroy()
    }

    private fun release(){
        job?.let { it.cancel() }
    }
}