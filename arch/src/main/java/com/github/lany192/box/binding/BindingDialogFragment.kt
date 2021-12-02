package com.github.lany192.box.binding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.github.lany192.dialog.BaseDialog

abstract class BindingDialogFragment<VB : ViewBinding> : BaseDialog() {
    lateinit var binding: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = getBinding(inflater, container)
        return binding.root
    }

    override fun getLayoutId(): Int{
        return 0
    }
}