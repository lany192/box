package com.github.lany192.box.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.github.lany192.box.databinding.ToolbarDefaultBinding
import com.github.lany192.box.view.ActivityBinding

abstract class ViewBindingActivity<VB : ViewBinding> : AppCompatActivity() {
    private lateinit var _binding: ActivityBinding

    val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding =  ActivityBinding(layoutInflater, getToolbar(), getContentView())
        setContentView(binding.root)
        init(savedInstanceState)
    }

    fun getToolbar(): ViewBinding {
        return ToolbarDefaultBinding.inflate(layoutInflater)
    }

    abstract fun getContentView(): VB

    abstract fun init(savedInstanceState: Bundle?)
}