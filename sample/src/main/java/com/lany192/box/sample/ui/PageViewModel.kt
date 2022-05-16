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
            block()
                .catch {
                    it.printStackTrace()
                    finishRequest()
                    showErrorView()
                }
                .collect { result ->
                    if (result.code == 0) {
                        if (result.data != null) {
                            if (refresh) {
                                resetItems(result.data!!.list)
                                refreshFinish()
                            } else {
                                addItems(result.data!!.list)
                                moreLoadFinish()
                            }
                            if (result.data!!.over) {
                                moreLoadEnd()
                            }
                        } else {
                            finishRequest()
                            showErrorView()
                        }
                    } else {
                        ToastUtils.show(result.msg)
                        finishRequest()
                        showErrorView()
                    }
                }
        }
    }
}