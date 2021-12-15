package com.github.lany192.box.sample.ui.settings.about

import android.content.Intent
import android.net.Uri
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
        binding.marketView.setOnClickListener{
            val uri = Uri.parse("market://details?id=$packageName")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            intent.setPackage("com.tencent.android.qqdownloader")
            startActivity(intent)
        }
        binding.protocolView.setOnClickListener {
            AppRouter.get().browser("百度也不知道", "https://www.baidu.com")
        }
        binding.privacyView.setOnClickListener {
            AppRouter.get().browser("百度也不知道", "https://www.baidu.com")
        }
    }
}