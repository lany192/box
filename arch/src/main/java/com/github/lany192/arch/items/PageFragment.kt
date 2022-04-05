package com.github.lany192.arch.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.lany192.arch.databinding.FragmentPageBinding
import com.scwang.smart.refresh.layout.SmartRefreshLayout

/**
 * 通用布局
 */
abstract class PageFragment<VM : ItemsViewModel> : ItemsFragment<VM, FragmentPageBinding>() {

    override fun getRefreshLayout(): SmartRefreshLayout {
        return binding.itemsView.refreshLayout
    }

    override fun getRecyclerView(): RecyclerView {
        return binding.itemsView.recyclerView
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPageBinding {
        return FragmentPageBinding.inflate(inflater, container, false)
    }
}