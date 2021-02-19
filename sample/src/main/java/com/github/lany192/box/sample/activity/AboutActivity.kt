package com.github.lany192.box.sample.activity

import android.os.Bundle
import com.github.lany192.box.activity.ActivityConfig
import com.github.lany192.box.activity.BaseActivity
import com.github.lany192.box.sample.R

class AboutActivity : BaseActivity() {

    override fun getConfig(): ActivityConfig {
        return ActivityConfig().apply {
            layoutId = R.layout.activity_about
            statusBarDarkFont = true
            transparentStatusBar = true
            toolBarLayoutId = R.layout.toolbar_default
            keyboardEnable = true
            hasBackBtn = true
        }
    }

    override fun init(savedInstanceState: Bundle) {}
}