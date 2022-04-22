package com.github.lany192.arch.activity

import androidx.viewbinding.ViewBinding
import com.github.lany192.arch.databinding.ToolbarDefaultBinding
import com.github.lany192.arch.viewmodel.LifecycleViewModel
import com.github.lany192.binding.getBinding

/**
 * 使用默认toolbar
 */
abstract class BoxActivity<VM : LifecycleViewModel, VB : ViewBinding> :
    ModelBindingActivity<VM, VB, ToolbarDefaultBinding>() {

    override fun getToolbarBinding(): ToolbarDefaultBinding {
        return ToolbarDefaultBinding.inflate(layoutInflater)
    }

    override fun getContentBinding(): VB {
        return getClass<VB>(1).getBinding(layoutInflater)
    }
}