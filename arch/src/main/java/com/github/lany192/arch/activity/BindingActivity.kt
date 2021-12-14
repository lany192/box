package com.github.lany192.arch.activity

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding
import com.github.lany192.arch.binding.getBinding

/**
 * ViewBinding实现基类
 */
abstract class BindingActivity<VB : ViewBinding> : BaseActivity() {
    lateinit var binding: VB

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getBinding()
        setContentView(binding.root)
    }
}