package com.lany192.box.sample.ui.main.index.article

import androidx.lifecycle.viewModelScope
import com.github.lany192.arch.items.ItemsViewModel
import com.github.lany192.arch.utils.ListUtils
import com.hjq.toast.ToastUtils
import com.lany192.box.sample.repository.BoxRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(val repository: BoxRepository) :
    ItemsViewModel() {

    override fun request(refresh: Boolean) {
        viewModelScope.launch {
            repository.getArticleList(page)
                .onStart {
                    log.i("接口开始")
                }.onCompletion {
                    log.i("接口结束")
                }.collect {
                    if (it.code == 0) {
                        if (ListUtils.isEmpty(it.data?.datas)) {
                            moreLoadEnd()
                        } else {
                            if (refresh) {
                                resetItems(it.data?.datas)
                                refreshFinish()
                            } else {
                                addItems(it.data?.datas)
                                moreLoadFinish()
                            }
                        }
                    } else {
                        ToastUtils.show(it.msg)
                        finishRequest()
                    }
                }
        }
    }
}