package com.github.lany192.arch.fragment

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/**
 * ViewBinding实现基类
 */
abstract class ModelFragment<VM : ViewModel, VB : ViewBinding> : BindingFragment<VB>() {
    lateinit var viewModel: VM

    @CallSuper
    override fun init() {
        val clazz =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>
        viewModel = createDefaultViewModel(clazz)
    }

    protected fun createDefaultViewModel(clazz: Class<VM>): VM {
        return getViewModel(clazz)
    }
}