package com.github.lany192.box.sample.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import com.alibaba.android.arouter.AppRouter
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.activity.SimpleActivity
import com.github.lany192.box.sample.databinding.ActivitySplashBinding
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/ui/splash")
@SuppressLint("CustomSplashScreen")
class SplashActivity : SimpleActivity<SplashViewModel, ActivitySplashBinding>() {

    override fun initImmersionBar(): ImmersionBar {
        return ImmersionBar.with(this)
            .hideBar(BarHide.FLAG_HIDE_BAR)
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
        viewModel.welcome.observe(this) { s: String? -> binding.textView.text = s }
        Handler().postDelayed({
            AppRouter.startMain()
            finish()
        }, 1000)
    }
}