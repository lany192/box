package com.github.lany192.box.binding

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * ViewBinding实现基类
 */
abstract class BindingActivity<V : ViewBinding> : AppCompatActivity() {
    lateinit var binding: V

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getBinding()
        setContentView(binding.root)
    }
}