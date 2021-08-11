package com.github.lany192.box.binding

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.viewbinding.ViewBinding

/**
 * ViewBinding实现基类
 */
abstract class BindingActivity<VB : ViewBinding> : AppCompatActivity() {
    lateinit var binding: VB
    lateinit var controller: WindowInsetsControllerCompat

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getBinding()
        controller = ViewCompat.getWindowInsetsController(binding.root)!!
        setContentView(binding.root)
    }
}