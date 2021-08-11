package com.github.lany192.box.binding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding

/**
 * ViewBinding实现基类
 */
abstract class BindingDialogFragment<VB : ViewBinding> : DialogFragment() {
    lateinit var binding: VB
    lateinit var controller: WindowInsetsControllerCompat

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = getBinding(inflater, container)
        controller = ViewCompat.getWindowInsetsController(binding.root)!!
        return binding.root
    }
}