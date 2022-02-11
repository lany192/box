package com.github.lany192.arch.activity

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/**
 * ViewBinding实现基类
 */
abstract class ViewModelActivity<VM : ViewModel, VB : ViewBinding> : BindingActivity<VB>() {
    lateinit var viewModel: VM

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val clazz = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>
        viewModel = createDefaultViewModel(clazz)
    }

    protected fun createDefaultViewModel(clazz: Class<VM>): VM {
        return getViewModel(clazz)
    }
}