package com.github.lany192.box.sample.ui.settings.about

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.AppRouter
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.activity.BindingActivity
import com.github.lany192.box.sample.databinding.ActivityAboutBinding
import com.gyf.immersionbar.ImmersionBar
import com.hjq.toast.ToastUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/ui/about")
class AboutActivity : BindingActivity<ActivityAboutBinding>() {

    private val viewModel: AboutViewModel by viewModels()

    override fun initImmersionBar() {
        ImmersionBar.with(this)
            .transparentStatusBar()
            .statusBarDarkFont(true)
            .titleBar(binding.toolbar)
            .init()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.toolbar.setNavigationOnClickListener { finish() }
        //
        viewModel.loading.observe(this, Observer { it ->
            //是是是
            ToastUtils.show(it)
        })
        binding.protocolView.setOnClickListener {
            AppRouter.get().browser("百度也不知道", "https://www.baidu.com")
        }
        binding.privacyView.setOnClickListener {
            AppRouter.get().browser("百度也不知道", "https://www.baidu.com")
        }
    }
}