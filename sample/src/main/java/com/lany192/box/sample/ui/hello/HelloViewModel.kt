package com.lany192.box.sample.ui.hello

import com.github.lany192.arch.items.PageViewModel
import com.lany192.box.sample.repository.BoxRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HelloViewModel @Inject constructor(var repository: BoxRepository) : PageViewModel() {

    override fun request(refresh: Boolean) {
        getPageInfo(refresh) { repository.getHomeArticles(page) }
    }
}