package com.github.lany192.box.sample.activity

import android.os.Bundle
import com.github.lany192.box.activity.ViewBindingActivity
import com.github.lany192.box.sample.databinding.ActivityHelloBinding

class HelloActivity : ViewBindingActivity<ActivityHelloBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.showTextVew.text = "哈哈"
    }

    override fun getViewBinding(): ActivityHelloBinding {
        return ActivityHelloBinding.inflate(layoutInflater)
    }
}