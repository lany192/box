package com.github.lany192.box.sample.ui.about

import android.os.Bundle
import com.github.lany192.box.activity.BindingActivity
import com.github.lany192.box.sample.R
import com.github.lany192.box.sample.databinding.ActivityAboutBinding
import com.gyf.immersionbar.ImmersionBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutActivity : BindingActivity<ActivityAboutBinding>() {

    private var viewModel: AboutViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel(AboutViewModel::class.java)
        ImmersionBar.with(this).transparentStatusBar().statusBarDarkFont(true).titleBar(binding.toolbar).init()

        binding.toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material)
        binding.toolbar.setNavigationOnClickListener { v -> finish() }
    }
}