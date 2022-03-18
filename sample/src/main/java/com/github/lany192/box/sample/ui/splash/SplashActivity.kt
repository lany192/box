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

    override fun initImmersionBar() {
        ImmersionBar.with(this)
            .hideBar(BarHide.FLAG_HIDE_BAR)
            .init()
    }

    override fun hasToolbar(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.welcome.observe(this) { s: String? -> binding.textView.text = s }
        Handler().postDelayed({
            AppRouter.get().main()
            finish()
        }, 1000)
    }
}