package com.github.lany192.arch.activity

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.github.lany192.binding.getBinding

/**
 * ViewBinding实现基类
 */
abstract class ModelBindingActivity<VM : ViewModel, CVB : ViewBinding, TVB : ViewBinding> :
    BindingActivity<CVB, TVB>() {
    lateinit var viewModel: VM

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getDefaultViewModel()
    }

    open fun getDefaultViewModel(): VM {
        return getViewModel(getClass(0))
    }

    override fun getToolbarBinding(): TVB {
        return getClass<TVB>(2).getBinding(layoutInflater)
    }

    override fun getContentBinding(): CVB {
        return getClass<CVB>(1).getBinding(layoutInflater)
    }
}