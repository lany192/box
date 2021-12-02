package com.github.lany192.box.sample.ui.about

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.github.lany192.box.binding.BindingActivity
import com.github.lany192.box.sample.databinding.ActivityAboutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutActivity : BindingActivity<ActivityAboutBinding>() {

    private var viewModel: AboutViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AboutViewModel::class.java)
    }
}