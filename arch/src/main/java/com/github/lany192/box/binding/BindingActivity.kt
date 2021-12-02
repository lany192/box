package com.github.lany192.box.binding

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding
import com.github.lany192.box.activity.BasicActivity
import com.github.lany192.box.activity.ModelActivity
import com.github.lany192.box.mvp.BaseView
import com.github.lany192.view.StateLayout
import com.github.lany192.view.StateLayout.OnRetryListener

/**
 * ViewBinding实现基类
 */
abstract class BindingActivity<VB : ViewBinding> : ModelActivity(), OnRetryListener, BaseView {
    lateinit var binding: VB
    lateinit var stateLayout: StateLayout

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stateLayout = StateLayout(this)
        stateLayout.setOnRetryListener(this)
        binding = getBinding()
        stateLayout.addView(binding.root)
        setContentView(stateLayout)
    }

    override fun onRetry() {
        log.i("点击重试")
    }

    override fun showEmpty() {
        stateLayout.showEmpty()
    }

    override fun showEmpty(msg: String?) {
        stateLayout.showEmpty(msg)
    }

    override fun showContent() {
        stateLayout.showContent()
    }

    override fun showNoWifi() {
        stateLayout.showNetwork()
    }

    override fun showError() {
        stateLayout.showError()
    }

    override fun showError(msg: String?) {
        stateLayout.showError(msg)
    }

    override fun showLoading() {
        stateLayout.showLoading()
    }
}