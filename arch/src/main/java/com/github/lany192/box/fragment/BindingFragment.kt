package com.github.lany192.box.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.github.lany192.box.binding.getBinding
import com.github.lany192.box.viewmodel.LifecycleViewModel
import com.github.lany192.view.StateLayout

/**
 * ViewBinding实现基类
 */
abstract class BindingFragment<VB : ViewBinding> : BasicFragment(), StateLayout.OnRetryListener {
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

    fun <T : LifecycleViewModel?> getFragmentViewModel(modelClass: Class<T>): T {
        val viewModel = ViewModelProvider(this)[modelClass]
        lifecycle.addObserver(viewModel!!)
        return viewModel
    }

    fun <T : LifecycleViewModel?> getActivityViewModel(modelClass: Class<T>): T {
        val viewModel = ViewModelProvider(requireActivity())[modelClass]
        lifecycle.addObserver(viewModel!!)
        return viewModel
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