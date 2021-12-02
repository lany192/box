package com.github.lany192.box.binding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.github.lany192.box.fragment.BasicFragment
import com.github.lany192.box.fragment.ModelFragment
import com.github.lany192.box.mvp.BaseView
import com.github.lany192.view.StateLayout

/**
 * ViewBinding实现基类
 */
abstract class BindingFragment<VB : ViewBinding> : ModelFragment(), StateLayout.OnRetryListener, BaseView {
    lateinit var binding: VB
    lateinit var stateLayout: StateLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        stateLayout = StateLayout(context)
        stateLayout.setOnRetryListener(this)
        binding = getBinding(inflater, container)
        stateLayout.addView(binding.root)
        return stateLayout
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