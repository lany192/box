package com.github.lany192.arch.items

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter4.QuickAdapterHelper
import com.chad.library.adapter4.layoutmanager.QuickGridLayoutManager
import com.github.lany192.arch.adapter.MultiAdapter
import com.github.lany192.arch.fragment.VMVBFragment
import com.github.lany192.arch.utils.ListUtils
import com.scwang.smart.refresh.layout.SmartRefreshLayout

abstract class ItemsFragment<VM : ItemsViewModel, VB : ViewBinding> : VMVBFragment<VM, VB>() {
    abstract fun getRefreshLayout(): SmartRefreshLayout

    abstract fun getRecyclerView(): RecyclerView

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
        val layoutManager = QuickGridLayoutManager(requireContext(), getSpanCount())
        layoutManager.setOrientation(GridLayoutManager.VERTICAL)
        return layoutManager
    }

    fun addOnScrollListener(listener: RecyclerView.OnScrollListener) {
        recyclerView.addOnScrollListener(listener)
    }

    open fun getSpanCount(): Int {
        return 2
    }

    override fun init() {
        super.init()
        recyclerView = getRecyclerView()
        layoutManager = createLayoutManager()
        recyclerView.setLayoutManager(layoutManager)
        recyclerView.adapter = helper.adapter
        getRefreshLayout().setEnableLoadMore(true)
        getRefreshLayout().setOnLoadMoreListener {
            viewModel.onLoadMore()
        }
        getRefreshLayout().setEnableRefresh(viewModel.refreshEnable())
        if (viewModel.refreshEnable()) {
            getRefreshLayout().setOnRefreshListener {
                viewModel.onRefresh()
            }
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
                    getRefreshLayout().finishLoadMore()
                }

                ListState.MORE_LOAD_FINISH -> {
                    getRefreshLayout().finishRefresh()
                    getRefreshLayout().finishLoadMore()
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