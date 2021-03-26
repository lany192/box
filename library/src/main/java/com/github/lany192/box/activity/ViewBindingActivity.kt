package com.github.lany192.box.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class ViewBindingActivity<VB : ViewBinding> : AppCompatActivity() {
    private lateinit var _binding: VB

    protected val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewBinding()
        setContentView(_binding.root)
        init(savedInstanceState)
    }

    abstract fun getViewBinding(): VB

    abstract fun init(savedInstanceState: Bundle?)
}