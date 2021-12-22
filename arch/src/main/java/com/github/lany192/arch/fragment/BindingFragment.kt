package com.github.lany192.arch.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.github.lany192.arch.binding.getBinding

/**
 * ViewBinding实现基类
 */
abstract class BindingFragment<VB : ViewBinding> : BaseFragment() {
    lateinit var binding: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = getBinding(inflater, container)
        initView()
        return binding.root
    }

    abstract fun initView()
}