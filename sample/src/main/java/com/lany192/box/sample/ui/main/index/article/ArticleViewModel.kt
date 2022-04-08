package com.lany192.box.sample.ui.main.index.article

import androidx.lifecycle.viewModelScope
import com.github.lany192.arch.items.ItemsViewModel
import com.lany192.box.sample.data.bean.ArticleList
import com.lany192.box.sample.data.bean.StateLiveData
import com.lany192.box.sample.repository.BoxRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(private val repository: BoxRepository) :
    ItemsViewModel() {
    var sss = StateLiveData<ArticleList>()

    override fun request(refresh: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getArticleList(page, sss)
        }

//
//        apiService.getHomeArticles(page)
//            .subscribe(object : ApiCallback<ArticleList> {
//
//                override fun onSuccess(msg: String, result: ArticleList) {
//                    if (ListUtils.isEmpty(result.datas)) {
//                        moreLoadEnd()
//                    } else {
//                        if (refresh) {
//                            resetItems(result.datas)
//                            refreshFinish()
//                        } else {
//                            addItems(result.datas)
//                            moreLoadFinish()
//                        }
//                    }
//                }
//
//                override fun onFailure(msg: String, code: Int) {
//                    ToastUtils.show(msg)
//                    finishRequest()
//                }
//            })
    }
}