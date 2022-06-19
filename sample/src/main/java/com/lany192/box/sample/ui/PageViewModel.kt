package com.lany192.box.sample.ui

import androidx.lifecycle.viewModelScope
import com.github.lany192.arch.items.ItemsViewModel
import com.hjq.toast.ToastUtils
import com.lany192.box.sample.data.bean.ApiResult
import com.lany192.box.sample.data.bean.PageInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import java.util.function.Function

abstract class PageViewModel : ItemsViewModel() {

    fun <T : PageInfo<*>> getPageInfo(
        refresh: Boolean,
        block: suspend () -> Flow<ApiResult<T>>
    ) {
        getPageInfo(refresh, block) { t -> t.list }
    }

    fun <T : PageInfo<*>> getPageInfo(
        refresh: Boolean,
        block: suspend () -> Flow<ApiResult<T>>,
        function: Function<T, List<Any?>>
    ) {
        viewModelScope.launch {
            block()
                .catch {
                    it.printStackTrace()
                    requestError()
                    showErrorView()
                }
                .onCompletion {
                    showContentView()
                }
                .collect { result ->
                    if (result.code == 0) {
                        val data = result.result
                        if (data != null) {
                            val items = function.apply(data).filterNotNull()
                            if (refresh) {
                                resetItems(items)
                                refreshFinish()
                            } else {
                                addItems(items)
                                moreLoadFinish()
                            }
                            if (data.over) {
                                moreLoadEnd()
                            }
                        } else {
                            requestError()
                        }
                    } else {
                        ToastUtils.show(result.msg)
                        requestError()
                    }
                }
        }
    }
}