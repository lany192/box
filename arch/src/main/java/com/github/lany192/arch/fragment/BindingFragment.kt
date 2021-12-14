package com.github.lany192.arch.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.github.lany192.arch.BoxApplication
import com.github.lany192.arch.binding.getBinding
import com.github.lany192.arch.viewmodel.BaseViewModel
import com.github.lany192.arch.viewmodel.LifecycleViewModel

/**
 * ViewBinding实现基类
 */
abstract class BindingFragment<VB : ViewBinding> : BasicFragment() {
    lateinit var binding: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = getBinding(inflater, container)
        return binding.root
    }
}