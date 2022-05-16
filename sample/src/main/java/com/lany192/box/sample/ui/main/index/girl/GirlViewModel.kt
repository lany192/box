package com.lany192.box.sample.ui.main.index.girl

import com.lany192.box.sample.repository.BoxRepository
import com.lany192.box.sample.ui.PageViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GirlViewModel @Inject constructor(val repository: BoxRepository) : PageViewModel() {

    override fun request(refresh: Boolean) {
        getPageInfo(refresh) { repository.getSquareArticleList(page) }
    }
}