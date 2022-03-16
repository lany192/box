package com.github.lany192.box.sample.ui.login

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.activity.ViewModelActivity
import com.github.lany192.box.sample.databinding.ActivityLoginBinding
import com.gyf.immersionbar.ImmersionBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/ui/login")
class LoginActivity : ViewModelActivity<LoginViewModel, ActivityLoginBinding>() {

    override fun initImmersionBar() {
        ImmersionBar.with(this)
            .transparentStatusBar()
            .statusBarDarkFont(true)
            .navigationBarColor(android.R.color.white)
            .navigationBarDarkIcon(true)
            .titleBar(binding.toolbar)
            .init()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log.i("test")
    }
}