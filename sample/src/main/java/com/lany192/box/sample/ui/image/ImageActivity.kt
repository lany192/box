package com.lany192.box.sample.ui.image

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alibaba.android.arouter.facade.annotation.Route
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

        val initPaddingTop = binding.toolbar.paddingTop
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            binding.toolbar.setPadding(
                binding.toolbar.getPaddingLeft(), initPaddingTop + systemBars.top,
                binding.toolbar.getPaddingRight(), binding.toolbar.paddingBottom
            )
            Log.i("高度：", "结果：" + systemBars.top + "，结果：" + systemBars.bottom)
            insets
        }
    }
}