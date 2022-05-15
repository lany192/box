package com.lany192.box.sample.ui

import com.github.lany192.arch.items.ItemsViewModel
import com.hjq.toast.ToastUtils
import com.lany192.box.sample.data.bean.ApiResult
import com.lany192.box.sample.data.bean.PageInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

abstract class PageViewModel : ItemsViewModel() {

    suspend fun <T> request(refresh: Boolean, block: suspend () -> Flow<ApiResult<PageInfo<T>>>) {
        block().catch { finishRequest() }.collect { result ->
            if (result.code == 0) {
                result.data?.let {
                    if (refresh) {
                        resetItems(it.list)
                        refreshFinish()
                    } else {
                        addItems(it.list)
                        moreLoadFinish()
                    }
                    if (!it.hasNext) {
                        moreLoadEnd()
                    }
                } ?: finishRequest()
            } else {
                ToastUtils.show(result.msg)
                finishRequest()
            }
        }
    }
}