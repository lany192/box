package com.lany192.box.sample.ui.main.index.article

import com.lany192.box.sample.repository.BoxRepository
import com.lany192.box.sample.ui.PageViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(val repository: BoxRepository) :
    PageViewModel() {

    override fun request(refresh: Boolean) {
        getPageInfo(refresh) { repository.getArticleList(page) }
//
//        viewModelScope.launch {
//            repository.getArticleList(page)
//                .onStart {
//                    log.i("接口开始")
//                }.onCompletion {
//                    log.i("接口结束")
//                }.collect { result ->
//                    if (result.code == 0) {
//                        result.data?.let {
//                            if (refresh) {
//                                resetItems(it.datas)
//                                refreshFinish()
//                            } else {
//                                addItems(it.datas)
//                                moreLoadFinish()
//                            }
//                            if (it.over) {
//                                moreLoadEnd()
//                            }
//                        } ?: finishRequest()
//                    } else {
//                        ToastUtils.show(result.msg)
//                        finishRequest()
//                    }
//                }
//        }
    }
}