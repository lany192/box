package com.github.lany192.box.sample.ui.about

import android.os.Bundle
import com.github.lany192.box.activity.BindingActivity
import com.github.lany192.box.sample.databinding.ActivityAboutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutActivity : BindingActivity<ActivityAboutBinding>() {

    private var viewModel: AboutViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel(AboutViewModel::class.java)
    }
}