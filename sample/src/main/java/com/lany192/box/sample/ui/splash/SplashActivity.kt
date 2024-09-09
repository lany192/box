package com.lany192.box.sample.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.activity.ViewModelActivity
import com.github.lany192.extension.load
import com.github.lany192.extension.postDelayedOnLifecycle
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import com.lany192.box.sample.R
import com.lany192.box.sample.databinding.ActivitySplashBinding
import com.lany192.box.sample.ui.main.MainRouter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
@Route(path = "/ui/splash")
@SuppressLint("CustomSplashScreen")
class SplashActivity : ViewModelActivity<SplashViewModel, ActivitySplashBinding>() {

    override fun initImmersionBar() {
        ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_BAR).init()
    }

    override fun getViewBinding(): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //防止部分手机点击桌面图标后重启应用
        if (!isTaskRoot) {
            finish()
        }
        binding.image.load(R.mipmap.demo)
        viewModel.welcome.observe(this) { s: String? -> binding.textView.text = s }
        postDelayedOnLifecycle(3000) {
            MainRouter.start()
            finish()
        }
    }
}