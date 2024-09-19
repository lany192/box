package com.lany192.box.sample.ui.image

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.utils.ViewUtils
import com.github.lany192.dialog.InputDialog
import com.lany192.box.sample.databinding.ActivityImageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/ui/image")
class ImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewUtils.applyWindowInsets(binding.root, binding.toolbar)
        binding.back.setOnClickListener { finish() }
        binding.bottom.setOnClickListener {
            InputDialog().apply {
                title = "输入框"
                message = "请输入内容"
            }.show()
        }
    }
}