package com.github.lany192.arch.items

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter4.QuickAdapterHelper
import com.chad.library.adapter4.layoutmanager.QuickGridLayoutManager
import com.chad.library.adapter4.loadState.LoadState
import com.chad.library.adapter4.loadState.trailing.TrailingLoadStateAdapter
import com.github.lany192.arch.adapter.MultiAdapter
import com.github.lany192.arch.fragment.VMVBFragment
import com.github.lany192.arch.utils.ListUtils
import com.scwang.smart.refresh.layout.SmartRefreshLayout

abstract class ItemsFragment <VM : ItemsViewModel, VB : ViewBinding> : VMVBFragment<VM, VB>() {
    abstract fun getRefreshLayout(): SmartRefreshLayout

    abstract fun getRecyclerView(): RecyclerView

    private val mAdapter by lazy(LazyThreadSafetyMode.NONE) {
        MultiAdapter(mutableListOf())
    }

    private val helper by lazy(LazyThreadSafetyMode.NONE) {
        QuickAdapterHelper.Builder(mAdapter)
            .setTrailingLoadStateAdapter(object : TrailingLoadStateAdapter.OnTrailingListener {
                override fun onLoad() {
                    viewModel.onLoadMore()
                }

                override fun onFailRetry() {
                    viewModel.onRefresh()
                }

                override fun isAllowLoading(): Boolean {
                    return true
                }
            })
            .build()
    }

    fun <T : Any, VB : ViewBinding> register(binder: ItemBinder<T, VB>) {
        mAdapter.register(binder)
    }

    fun getLayoutManager(): RecyclerView.LayoutManager {
        val layoutManager = QuickGridLayoutManager(requireContext(), getSpanCount())
        layoutManager.setOrientation(GridLayoutManager.VERTICAL)
        return layoutManager;
    }

    open fun getSpanCount(): Int {
        return 2;
    }

    override fun init() {
        super.init()
        getRecyclerView().setLayoutManager(getLayoutManager());
        getRecyclerView().adapter = helper.adapter
        getRefreshLayout().setEnableLoadMore(false);
        getRefreshLayout().setEnableRefresh(viewModel.refreshEnable());
        if (viewModel.refreshEnable()) {
            getRefreshLayout().setOnRefreshListener { viewModel.onRefresh() };
        }
        //列表状态观察
        viewModel.listState.observe(this) {
            when (it) {//常量
                ListState.ERROR -> {
                    getRefreshLayout().finishRefresh()
                }

                ListState.REFRESHING -> {

                }

                ListState.REFRESH_FINISH -> {
                    getRefreshLayout().finishRefresh()
                }

                ListState.MORE_LOADING -> {

                }

                ListState.MORE_LOAD_END -> {
                    getRefreshLayout().finishRefresh()
                    //设置状态为未加载，并且没有分页数据了
                    helper.trailingLoadState = LoadState.NotLoading(false)
                }

                ListState.MORE_LOAD_FINISH -> {
                    getRefreshLayout().finishRefresh()
                    //设置状态为未加载，并且还有分页数据
                    helper.trailingLoadState = LoadState.NotLoading(true)
                }
            }
        }
        viewModel.items.observe(this) {
            mAdapter.submitList(it.items)
            if (ListUtils.isEmpty(it.items)) {
                mAdapter.stateView = getEmptyView()
            }
        }
    }
}