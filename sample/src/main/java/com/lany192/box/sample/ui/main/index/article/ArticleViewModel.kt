package com.lany192.box.sample.ui.main.index.article

import androidx.lifecycle.viewModelScope
import com.github.lany192.arch.items.ItemsViewModel
import com.github.lany192.arch.utils.ListUtils
import com.hjq.toast.ToastUtils
import com.lany192.box.sample.data.api.HttpCallback
import com.lany192.box.sample.data.bean.ArticleList
import com.lany192.box.sample.repository.BoxRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(private val repository: BoxRepository) :
    ItemsViewModel() {

    override fun request(refresh: Boolean) {
        log.i("1线程测试" + Thread.currentThread().name)
        viewModelScope.launch {
            log.i("2线程测试" + Thread.currentThread().name)
            repository.getArticleList(page, object : HttpCallback<ArticleList> {

                override fun onSuccess(msg: String, result: ArticleList) {
                    log.i("5线程测试" + Thread.currentThread().name)
                    if (ListUtils.isEmpty(result.datas)) {
                        moreLoadEnd()
                    } else {
                        if (refresh) {
                            resetItems(result.datas)
                            refreshFinish()
                        } else {
                            addItems(result.datas)
                            moreLoadFinish()
                        }
                    }
                }

                override fun onFailure(msg: String, code: Int) {
                    ToastUtils.show(msg)
                    finishRequest()
                }
            })
        }
    }
}