package com.github.lany192.arch.items

import androidx.lifecycle.viewModelScope
import com.github.lany192.arch.entity.ApiResult
import com.github.lany192.arch.entity.Page
import com.hjq.toast.ToastUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import java.util.function.Function

abstract class PageViewModel : ItemsViewModel() {
    /**
     * 请求分页数据
     */
    fun <T : Page<*>> requestPage(
        refresh: Boolean,
        block: suspend () -> Flow<ApiResult<T>>
    ) {
        requestPage(refresh, block) { t -> t.list }
    }

    /**
     * 请求分页数据
     */
    fun <T : Page<*>> requestPage(
        /**
         * 是否刷新
         */
        refresh: Boolean,
        /**
         * 数据来源
         */
        block: suspend () -> Flow<ApiResult<T>>,
        /**
         * 用于单个item的类型转换
         */
        function: Function<T, List<Any?>>
    ) {
        viewModelScope.launch {
            block()
                .onCompletion {
                    if (it != null) {
                        showErrorView(it.message.toString())
                    } else {
                        showContentView()
                    }
                }
                .collect {
                    if (isSuccess(it)) {
                        val result = it.result
                        if (result != null) {
                            val items = function.apply(result).filterNotNull()
                            if (refresh) {
                                resetItems(items)
                                refreshFinish()
                            } else {
                                addItems(items)
                                moreLoadFinish()
                            }
                            if (!result.hasNext()) {
                                moreLoadEnd()
                            }
                        } else {
                            requestError()
                        }
                    } else {
                        ToastUtils.show(it.msg)
                        requestError()
                    }
                }
        }
    }

    open fun <T> isSuccess(result: ApiResult<T>): Boolean {
        return result.code == 200
    }
}