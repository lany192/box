package com.github.lany192.arch.activity

import androidx.viewbinding.ViewBinding
import com.github.lany192.arch.databinding.ToolbarDefaultBinding
import com.github.lany192.binding.getBinding

abstract class ToolbarActivity<VB : ViewBinding> : BindingActivity<VB, ToolbarDefaultBinding>() {

    override fun getToolbarBinding(): ToolbarDefaultBinding {
        return ToolbarDefaultBinding.inflate(layoutInflater)
    }

    override fun getContentBinding(): VB {
        return getClass<VB>(0).getBinding(layoutInflater)
    }
}