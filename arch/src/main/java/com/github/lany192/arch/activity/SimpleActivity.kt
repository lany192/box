package com.github.lany192.arch.activity

import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.github.lany192.arch.databinding.ToolbarDefaultBinding
import com.github.lany192.binding.getBinding

abstract class SimpleActivity<VM : ViewModel, VB : ViewBinding> :
    ModelActivity<VM, VB, ToolbarDefaultBinding>() {

    override fun getToolbarBinding(): ToolbarDefaultBinding {
        return ToolbarDefaultBinding.inflate(layoutInflater)
    }

    override fun getContentBinding(): VB {
        return getClass<VB>(1).getBinding(layoutInflater)
    }
}