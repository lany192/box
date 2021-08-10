package com.github.lany192.box.binding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * ViewBinding实现基类
 */
abstract class BindingFragment<V : ViewBinding> : Fragment() {
    lateinit var binding: V

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = getBinding(inflater, container)
        return binding.root
    }
}