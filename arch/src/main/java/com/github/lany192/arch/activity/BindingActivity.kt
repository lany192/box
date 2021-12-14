package com.github.lany192.arch.activity

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.github.lany192.arch.BoxApplication
import com.github.lany192.arch.binding.getBinding
import com.github.lany192.arch.viewmodel.BaseViewModel
import com.github.lany192.arch.viewmodel.LifecycleViewModel

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