package com.lany192.box.sample.ui.blur

import android.graphics.Outline
import android.os.Bundle
import android.view.View
import android.view.ViewOutlineProvider
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.lany192.box.sample.databinding.ActivityBlurBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/ui/blur")
class BlurActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityBlurBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.blurView.setupWith(binding.root)
            .setFrameClearDrawable(window.decorView.background)
            .setBlurRadius(15f)
        binding.blurView.setClipToOutline(true)
        binding.blurView.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                binding.blurView.background.getOutline(outline)
                outline.alpha = 1f
            }
        }
    }
}