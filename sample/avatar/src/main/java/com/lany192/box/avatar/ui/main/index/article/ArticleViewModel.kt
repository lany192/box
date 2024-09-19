package com.lany192.box.avatar.ui.main.index.article

import com.github.lany192.arch.entity.ApiResult
import com.github.lany192.arch.items.PageViewModel
import com.lany192.box.network.repository.BoxRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(val repository: BoxRepository) :
    PageViewModel() {

    override fun <T> isSuccess(result: ApiResult<T>): Boolean {
        return result.code == 0
    }

    override fun refreshEnable(): Boolean {
        return false
    }

    override fun request(refresh: Boolean) {
        requestPage(refresh) { repository.getArticleList(page) }
    }
}