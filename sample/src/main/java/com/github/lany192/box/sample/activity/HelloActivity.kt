package com.github.lany192.box.sample.activity

import android.os.Bundle
import android.util.Log
import androidx.viewbinding.ViewBinding
import com.github.lany192.box.activity.ViewBindingActivity
import com.github.lany192.box.sample.databinding.ActivityHelloBinding
import com.github.lany192.box.view.ActivityBinding

class HelloActivity : ViewBindingActivity<ActivityHelloBinding>() {

    override fun init(savedInstanceState: Bundle?) {
        binding.getContent<ActivityHelloBinding>().showTextVew.text = "哈哈"
    }

    override fun getContentView(): ActivityHelloBinding {
        return ActivityHelloBinding.inflate(layoutInflater)
    }

    override fun init(savedInstanceState: Bundle?) {
        //
    }
}