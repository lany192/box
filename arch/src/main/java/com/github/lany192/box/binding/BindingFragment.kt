package com.github.lany192.box.binding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.elvishew.xlog.Logger
import com.elvishew.xlog.XLog
import com.github.lany192.box.fragment.BasicFragment
import com.github.lany192.view.StateLayout

/**
 * ViewBinding实现基类
 */
abstract class BindingFragment<VB : ViewBinding> : BasicFragment() , StateLayout.OnRetryListener {
    protected var log: Logger.Builder = XLog.tag(javaClass.simpleName)
    lateinit var binding: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val stateLayout = StateLayout(context)
        stateLayout.setOnRetryListener(this)
        binding = getBinding(inflater, container)
        stateLayout.addView(binding.root)
        return stateLayout
    }

    override fun onRetry() {
        log.i("点击重试")
    }
}