package com.lany192.box.avatar.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.activity.ViewModelActivity
import com.github.lany192.extension.load
import com.github.lany192.extension.postDelayedOnLifecycle
import com.lany192.box.avatar.R
import com.lany192.box.avatar.databinding.ActivitySplashBinding
import com.lany192.box.avatar.ui.main.MainRouter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
@Route(path = "/ui/splash")
@SuppressLint("CustomSplashScreen")
class SplashActivity : ViewModelActivity<SplashViewModel, ActivitySplashBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //防止部分手机点击桌面图标后重启应用
        if (!isTaskRoot) {
            finish()
        }
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, 0)
            insets
        }
        binding.image.load(R.mipmap.demo)
        viewModel.welcome.observe(this) { s: String? -> binding.textView.text = s }
        postDelayedOnLifecycle(3000) {
            MainRouter.start()
            finish()
        }
    }
}