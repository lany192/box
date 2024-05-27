package com.github.lany192.arch.items

import androidx.recyclerview.widget.RecyclerView
import com.github.lany192.arch.databinding.ActivityPageBinding
import com.github.lany192.arch.utils.BarUtils
import com.scwang.smart.refresh.layout.SmartRefreshLayout

/**
 * 通用布局
 */
abstract class PageActivity<VM : ItemsViewModel> : ItemsActivity<VM, ActivityPageBinding>() {

    override fun initImmersionBar() {
        BarUtils.init(this)
            .keyboardEnable(true)
            .titleBar(binding.toolbar)
            .init()
    }

    override fun createRefreshLayout(): SmartRefreshLayout {
        return binding.itemsView.refreshLayout
    }

    override fun createRecyclerView(): RecyclerView {
        return binding.itemsView.recyclerView
    }

    override fun getViewBinding(): ActivityPageBinding {
        return ActivityPageBinding.inflate(layoutInflater)
    }
}