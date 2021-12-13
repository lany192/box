package com.github.lany192.box.sample.ui.settings.about

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.github.lany192.box.activity.BindingActivity
import com.github.lany192.box.sample.R
import com.github.lany192.box.sample.databinding.ActivityAboutBinding
import com.gyf.immersionbar.ImmersionBar
import com.hjq.toast.ToastUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
        binding.toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material)
        binding.toolbar.setNavigationOnClickListener { finish() }

        viewModel.loading.observe(this, Observer { it ->
            ToastUtils.show(it)
        })
    }
}