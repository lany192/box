package com.github.lany192.box.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class ViewBindingFragment<VB : ViewBinding> : Fragment(){

    private lateinit var _binding: VB

    protected val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = getBinding(inflater, container)
        return _binding.root
    }

    protected abstract fun getBinding(inflater: LayoutInflater, viewGroup: ViewGroup?): VB
}