package com.lany192.box.sample.ui.database

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.activity.ViewBindingActivity
import com.github.lany192.arch.activity.ViewModelActivity
import com.gyf.immersionbar.ImmersionBar
import com.lany192.box.sample.R
import com.lany192.box.sample.databinding.ActivityDatabaseBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/ui/database")
class DatabaseActivity : ViewModelActivity<DatabaseViewModel, ActivityDatabaseBinding>() {

    override fun initImmersionBar(): ImmersionBar {
        return super.initImmersionBar().titleBar(binding.toolbar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.insert.setOnClickListener {
            viewModel.insert()
        }
        binding.query.setOnClickListener {
            viewModel.query()
        }
    }
}