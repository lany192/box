package com.github.lany192.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

abstract class BindingDialog<VB : ViewBinding> : DialogFragment() {
    lateinit var binding: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = createBinding(inflater, container)
        return binding.root
    }

    fun <V : ViewBinding> createBinding(inflater: LayoutInflater, container: ViewGroup?): V {
        return findClass().getBinding(inflater, container)
    }
}
