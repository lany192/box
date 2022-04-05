package com.lany192.box.sample.ui.main.index.article

import com.github.lany192.arch.items.ItemsViewModel
import com.github.lany192.arch.utils.ListUtils
import com.hjq.toast.ToastUtils
import com.lany192.box.sample.data.api.ApiCallback
import com.lany192.box.sample.data.api.ApiService
import com.lany192.box.sample.data.bean.ArticleList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(var apiService: ApiService) : ItemsViewModel() {

    override fun request(refresh: Boolean) {
        apiService.getHomeArticles(page)
            .subscribe(object : ApiCallback<ArticleList> {

                override fun onSuccess(msg: String, result: ArticleList) {
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