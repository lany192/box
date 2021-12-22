package com.github.lany192.arch.fragment

import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding
import com.github.lany192.arch.viewmodel.BaseViewModel
import java.lang.reflect.ParameterizedType

/**
 * ViewBinding实现基类
 */
abstract class ViewModelFragment<VM : BaseViewModel, VB : ViewBinding> : BindingFragment<VB>() {
    lateinit var viewModel: VM

    @CallSuper
    override fun initView() {
        viewModel =
            getFragmentViewModel((javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>)
    }
}