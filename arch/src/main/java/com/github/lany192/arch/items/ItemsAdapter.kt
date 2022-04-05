package com.github.lany192.arch.items

import com.chad.library.adapter.base.BaseBinderAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.BaseLoadMoreModule
import com.chad.library.adapter.base.module.LoadMoreModule

/**
 * 多布局适配器
 */
class ItemsAdapter : BaseBinderAdapter(), LoadMoreModule {

    override fun addLoadMoreModule(baseQuickAdapter: BaseQuickAdapter<*, *>): BaseLoadMoreModule {
        return BaseLoadMoreModule(baseQuickAdapter)
    }

    fun loadMoreComplete() {
        loadMoreModule.loadMoreComplete()
    }

    fun loadMoreFail() {
        loadMoreModule.loadMoreFail()
    }

    fun loadMoreEnd() {
        loadMoreModule.loadMoreEnd()
    }
}