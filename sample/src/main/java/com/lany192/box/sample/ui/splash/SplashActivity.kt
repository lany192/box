package com.lany192.box.sample.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.alibaba.android.arouter.SampleRouter
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.activity.BoxActivity
import com.github.lany192.utils.ImageUtils
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import com.lany192.box.sample.R
import com.lany192.box.sample.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
@Route(path = "/ui/splash")
@SuppressLint("CustomSplashScreen")
class SplashActivity : BoxActivity<SplashViewModel, ActivitySplashBinding>() {

    override fun initImmersionBar(): ImmersionBar {
        return ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_BAR)
    }

    override fun hasToolbar(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //防止部分手机点击桌面图标后重启应用
        if (!isTaskRoot) {
            finish()
        }
        ImageUtils.show(binding.image, R.mipmap.demo)
        viewModel.welcome.observe(this) { s: String? -> binding.textView.text = s }
        Handler(Looper.getMainLooper()).postDelayed({
            SampleRouter.startMain()
            finish()
        }, 3000)
    }
}