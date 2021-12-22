package com.github.lany192.arch.activity

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding
import com.github.lany192.arch.viewmodel.BaseViewModel
import java.lang.reflect.ParameterizedType

/**
 * ViewBinding实现基类
 */
abstract class ViewModelActivity<VM : BaseViewModel, VB : ViewBinding> : BindingActivity<VB>() {
    lateinit var viewModel: VM

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getActivityViewModel((javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>)
    }
}