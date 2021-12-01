package com.github.lany192.box.binding

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.elvishew.xlog.Logger
import com.elvishew.xlog.XLog
import com.github.lany192.view.StateLayout
import com.github.lany192.view.StateLayout.OnRetryListener

/**
 * ViewBinding实现基类
 */
abstract class BindingActivity<VB : ViewBinding> : AppCompatActivity(), OnRetryListener {
    protected var log: Logger.Builder = XLog.tag(javaClass.simpleName)
    lateinit var binding: VB

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val stateLayout = StateLayout(this)
        stateLayout.setOnRetryListener(this)
        binding = getBinding()
        stateLayout.addView(binding.root)
        setContentView(stateLayout)
    }

    override fun onRetry() {
        log.i("点击重试")
    }
}