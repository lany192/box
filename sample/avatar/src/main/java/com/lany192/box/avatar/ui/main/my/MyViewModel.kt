package com.lany192.box.avatar.ui.main.my

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.lany192.arch.viewmodel.LifecycleViewModel
import com.github.lany192.extension.toast
import com.lany192.box.network.data.bean.Area
import com.lany192.box.network.repository.BoxRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(private val repository: BoxRepository) :
    LifecycleViewModel() {
    private val items = MutableLiveData<List<Area>>()

    override fun onLazyLoad() {
        super.onLazyLoad()
        requestCityInfo()
    }

    private fun requestCityInfo() {
        viewModelScope.launch {
            repository.getCityList()
                .onStart {
                    log.i("接口开始")
                }.onCompletion {
                    log.i("接口结束")
                }.collect {
                    if (it.code == 0) {
                        items.postValue(it.data)
                    } else {
                        toast(it.msg)
                    }
                }
        }
    }
}