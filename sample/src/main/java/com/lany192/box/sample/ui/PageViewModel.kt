package com.lany192.box.sample.ui

import androidx.lifecycle.viewModelScope
import com.github.lany192.arch.items.ItemsViewModel
import com.hjq.toast.ToastUtils
import com.lany192.box.sample.data.bean.ApiResult
import com.lany192.box.sample.data.bean.PageInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

abstract class PageViewModel : ItemsViewModel() {

    fun <T : PageInfo<*>> getPageInfo(
        refresh: Boolean,
        block: suspend () -> Flow<ApiResult<T>>
    ) {
        viewModelScope.launch {
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
                        if (it.over) {
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
}