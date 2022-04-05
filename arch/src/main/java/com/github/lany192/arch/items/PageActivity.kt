package com.github.lany192.arch.items

import androidx.recyclerview.widget.RecyclerView
import com.github.lany192.arch.databinding.ActivityPageBinding
import com.github.lany192.arch.databinding.ToolbarDefaultBinding
import com.scwang.smart.refresh.layout.SmartRefreshLayout

/**
 * 通用布局
 */
abstract class PageActivity<VM : ListViewModel> :
    ListActivity<VM, ActivityPageBinding, ToolbarDefaultBinding>() {

    override fun getRefreshLayout(): SmartRefreshLayout {
        return binding.itemsView.refreshLayout
    }

    override fun getRecyclerView(): RecyclerView {
        return binding.itemsView.recyclerView
    }

    override fun getToolbarBinding(): ToolbarDefaultBinding {
        return ToolbarDefaultBinding.inflate(layoutInflater)
    }

    override fun getContentBinding(): ActivityPageBinding {
        return ActivityPageBinding.inflate(layoutInflater)
    }
}