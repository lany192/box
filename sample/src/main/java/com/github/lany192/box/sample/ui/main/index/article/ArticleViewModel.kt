package com.github.lany192.box.sample.ui.main.index.article

import com.github.lany192.arch.items.PageListViewModel
import com.github.lany192.arch.utils.ListUtils
import com.github.lany192.box.sample.data.api.ApiCallback
import com.github.lany192.box.sample.data.api.ApiService
import com.github.lany192.box.sample.data.bean.ArticleList
import com.hjq.toast.ToastUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor() : PageListViewModel() {
    @Inject
    lateinit var apiService: ApiService

    override fun request(refresh: Boolean) {
        apiService.getHomeArticles(page)
            .subscribe(object : ApiCallback<ArticleList> {

                override fun onSuccess(msg: String, result: ArticleList) {
                    if(ListUtils.isEmpty(result.datas)){

                    }else{
                        if (refresh) {
                            resetItems(result.datas)
                            finishRefresh()
                        } else {
                            addItems(result.datas)
                            finishLoadMore()
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