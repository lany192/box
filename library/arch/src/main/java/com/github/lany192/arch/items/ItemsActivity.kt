package com.github.lany192.arch.items

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter4.QuickAdapterHelper
import com.chad.library.adapter4.layoutmanager.QuickGridLayoutManager
import com.github.lany192.arch.activity.VMVBActivity
import com.github.lany192.arch.adapter.MultiAdapter
import com.github.lany192.arch.utils.ListUtils
import com.scwang.smart.refresh.layout.SmartRefreshLayout

abstract class ItemsActivity<VM : ItemsViewModel, VB : ViewBinding> : VMVBActivity<VM, VB>() {
    protected lateinit var refreshLayout: SmartRefreshLayout
    protected lateinit var layoutManager: RecyclerView.LayoutManager
    protected lateinit var recyclerView: RecyclerView

    private val mAdapter by lazy(LazyThreadSafetyMode.NONE) {
        MultiAdapter(mutableListOf())
    }

    private val helper by lazy(LazyThreadSafetyMode.NONE) {
        QuickAdapterHelper.Builder(mAdapter).build()
    }

    fun <T : Any, VB : ViewBinding> register(binder: ItemBinder<T, VB>) {
        mAdapter.register(binder)
    }

    open fun createLayoutManager(): RecyclerView.LayoutManager {
        val layoutManager = QuickGridLayoutManager(this, getSpanCount())
        layoutManager.setOrientation(GridLayoutManager.VERTICAL)
        return layoutManager
    }

    open fun getSpanCount(): Int {
        return 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        refreshLayout = createRefreshLayout()
        recyclerView = createRecyclerView()
        layoutManager = createLayoutManager()

        recyclerView.setLayoutManager(layoutManager)
        recyclerView.adapter = helper.adapter
        refreshLayout.setEnableLoadMore(true)
        refreshLayout.setOnLoadMoreListener {
            viewModel.onLoadMore()
        }
        refreshLayout.setEnableRefresh(viewModel.refreshEnable());
        if (viewModel.refreshEnable()) {
            refreshLayout.setOnRefreshListener {
                viewModel.onRefresh()
            }
        }
        //列表状态观察
        viewModel.listState.observe(this) {
            when (it) {
                ListState.ERROR -> {
                    refreshLayout.finishRefresh()
                }

                ListState.REFRESHING -> {

                }

                ListState.REFRESH_FINISH -> {
                    refreshLayout.finishRefresh()
                }

                ListState.MORE_LOADING -> {

                }

                ListState.MORE_LOAD_END -> {
                    refreshLayout.finishRefresh()
                    refreshLayout.finishLoadMore()
                }

                ListState.MORE_LOAD_FINISH -> {
                    refreshLayout.finishRefresh()
                    refreshLayout.finishLoadMore()
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

    abstract fun createRefreshLayout(): SmartRefreshLayout

    abstract fun createRecyclerView(): RecyclerView
}