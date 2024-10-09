package com.lany192.box.demo.ui.main.index.girl

import com.github.lany192.arch.entity.ApiResult
import com.github.lany192.arch.items.PageViewModel
import com.lany192.box.network.repository.BoxRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GirlViewModel @Inject constructor(val repository: BoxRepository) : PageViewModel() {

    override fun <T> isSuccess(result: ApiResult<T>): Boolean {
        return result.code == 0
    }

    override fun request(refresh: Boolean) {
        requestPage(refresh) { repository.getSquareArticleList(page) }
    }
}