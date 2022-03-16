package com.github.lany192.arch.items

import androidx.recyclerview.widget.RecyclerView
import com.github.lany192.arch.databinding.FragmentPageBinding
import com.scwang.smart.refresh.layout.SmartRefreshLayout

/**
 * 通用布局
 */
abstract class PageFragment<VM : ListViewModel> : ListFragment<VM, FragmentPageBinding>(){

    override fun getRefreshLayout(): SmartRefreshLayout {
        return binding.refreshLayout
    }

    override fun getRecyclerView(): RecyclerView {
        return binding.recyclerView
    }
}