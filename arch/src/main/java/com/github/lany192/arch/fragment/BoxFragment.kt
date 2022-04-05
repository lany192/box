package com.github.lany192.arch.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.github.lany192.binding.getBinding

/**
 * ViewBinding实现基类
 */
abstract class BoxFragment<VM : ViewModel, VB : ViewBinding> : BindingFragment<VB>() {
    lateinit var viewModel: VM

    @CallSuper
    override fun init() {
        viewModel = getDefaultViewModel()
    }

    open fun getDefaultViewModel(): VM {
        return getViewModel(getClass(0))
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB {
        return getClass<VB>(1).getBinding(inflater, container)
    }
}