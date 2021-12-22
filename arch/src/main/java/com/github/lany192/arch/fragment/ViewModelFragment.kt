package com.github.lany192.arch.fragment

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/**
 * ViewBinding实现基类
 */
abstract class ViewModelFragment<VM : ViewModel, VB : ViewBinding> : BindingFragment<VB>() {
    lateinit var viewModel: VM

    @CallSuper
    override fun initView() {
        viewModel =
            getFragmentViewModel((javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>)
    }
}