package com.lany192.box.demo.ui.main.index.city

import androidx.lifecycle.viewModelScope
import com.github.lany192.arch.items.ItemsViewModel
import com.github.lany192.extension.toast
import com.hjq.toast.Toaster
import com.lany192.box.network.repository.BoxRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(val repository: BoxRepository) : ItemsViewModel() {

    override fun request(refresh: Boolean) {
        viewModelScope.launch {
            repository.getCityList()
                .onStart {
                    log.i("接口开始")
                }.onCompletion {
                    log.i("接口结束")
                }.collect {
                    if (it.code == 0) {
                        if (refresh) {
                            resetItems(it.data)
                            refreshFinish()
                        } else {
                            addItems(it.data)
                            moreLoadFinish()
                        }
                        moreLoadEnd()
                    } else {
                        toast(it.msg)
                    }
                }
        }
    }
}