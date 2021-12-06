package com.github.lany192.box.activity

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.github.lany192.box.binding.getBinding
import com.github.lany192.box.mvvm.LifecycleViewModel

/**
 * ViewBinding实现基类
 */
abstract class BindingActivity<VB : ViewBinding> : BaseActivity() {
    lateinit var binding: VB

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getBinding()
        setRootView(binding.root)
    }

    fun <T : LifecycleViewModel?> getViewModel(modelClass: Class<T>): T {
        val viewModel = ViewModelProvider(this)[modelClass]
        lifecycle.addObserver(viewModel!!)
        return viewModel
    }
}