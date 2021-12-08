package com.github.lany192.box.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.github.lany192.box.binding.getBinding
import com.github.lany192.box.viewmodel.LifecycleViewModel

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

    fun <T : LifecycleViewModel?> getFragmentViewModel(modelClass: Class<T>): T {
        val viewModel = ViewModelProvider(this)[modelClass]
        lifecycle.addObserver(viewModel!!)
        return viewModel
    }

    fun <T : LifecycleViewModel?> getActivityViewModel(modelClass: Class<T>): T {
        val viewModel = ViewModelProvider(requireActivity())[modelClass]
        lifecycle.addObserver(viewModel!!)
        return viewModel
    }

}