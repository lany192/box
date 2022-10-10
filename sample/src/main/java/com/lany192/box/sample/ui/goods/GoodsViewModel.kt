package com.lany192.box.sample.ui.goods

import com.github.lany192.arch.entity.ApiResult
import com.github.lany192.arch.items.PageViewModel
import com.lany192.box.sample.repository.BoxRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GoodsViewModel @Inject constructor(var repository: BoxRepository) : PageViewModel() {

    override fun <T> isSuccess(result: ApiResult<T>): Boolean {
        return result.code == 0
    }

    override fun loadMoreEnable(): Boolean {
        return false
    }

    override fun request(refresh: Boolean) {
        requestPage(refresh) { repository.getHomeArticles(page) }
    }
}