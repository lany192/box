package com.lany192.box.sample.ui.goods

import androidx.lifecycle.viewModelScope
import com.github.lany192.arch.items.ItemsViewModel
import com.lany192.box.network.data.api.ApiService
import com.lany192.box.network.data.bean.ViewPagerItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoodsViewModel @Inject constructor(var service: ApiService) : ItemsViewModel() {

    override fun loadMoreEnable(): Boolean {
        return false
    }

    override fun request(refresh: Boolean) {
        viewModelScope.launch {
            val items = mutableListOf<Any>()
            items.addAll(service.getHomeArticles(1).data.list)
            items.add(ViewPagerItem())
            resetItems(items)
            refreshFinish()
        }
    }
}