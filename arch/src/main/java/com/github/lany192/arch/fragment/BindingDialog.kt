package com.github.lany192.arch.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.github.lany192.arch.BoxApplication
import com.github.lany192.arch.binding.getBinding
import com.github.lany192.arch.viewmodel.LifecycleViewModel
import com.github.lany192.dialog.BasicDialog

abstract class BindingDialog<VB : ViewBinding> : BasicDialog() {
    lateinit var binding: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = getBinding(inflater, container)
        return binding.root
    }

    fun <T : LifecycleViewModel>  getViewModel(modelClass: Class<T>): T {
        val viewModel = ViewModelProvider(this)[modelClass]
        lifecycle.addObserver(viewModel)
        return viewModel
    }

    fun <T : LifecycleViewModel> getActivityViewModel(modelClass: Class<T>): T {
        val viewModel = ViewModelProvider(requireActivity())[modelClass]
        lifecycle.addObserver(viewModel)
        return viewModel
    }

    fun <T : ViewModel> getAndroidViewModel(modelClass: Class<T>): T {
        return ViewModelProvider(requireActivity().applicationContext as BoxApplication)[modelClass]
    }

    override fun getLayoutId(): Int {
        return 0
    }
}