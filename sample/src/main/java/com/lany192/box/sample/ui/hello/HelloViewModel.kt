package com.lany192.box.sample.ui.hello

import androidx.lifecycle.viewModelScope
import com.github.lany192.arch.items.ItemsViewModel
import com.hjq.toast.ToastUtils
import com.lany192.box.sample.repository.BoxRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HelloViewModel @Inject constructor(var repository: BoxRepository) : ItemsViewModel() {

    override fun request(refresh: Boolean) {
        viewModelScope.launch {
            repository.getHomeArticles(page).collect {
                if (it.isSuccess) {
                    it.result?.let { r ->
                        if (refresh) {
                            resetItems(r.datas)
                            refreshFinish()
                        } else {
                            addItems(r.datas)
                            moreLoadFinish()
                        }
                    }
                } else {
                    ToastUtils.show(it.msg)
                    requestError()
                }
            }
        }
    }
}