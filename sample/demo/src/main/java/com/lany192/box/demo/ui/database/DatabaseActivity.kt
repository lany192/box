package com.lany192.box.demo.ui.database

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.activity.ViewModelActivity
import com.lany192.box.demo.databinding.ActivityDatabaseBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
@Route(path = "/ui/database")
class DatabaseActivity : ViewModelActivity<DatabaseViewModel, ActivityDatabaseBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding.insert.setOnClickListener {
            viewModel.insert()
        }
        binding.query.setOnClickListener {
            viewModel.query()
        }
        lifecycleScope.launch {
            viewModel.results.collect {
                binding.result.text = it.toString()
            }
        }
    }
}