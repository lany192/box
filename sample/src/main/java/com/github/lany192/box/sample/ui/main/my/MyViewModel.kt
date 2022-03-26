package com.github.lany192.box.sample.ui.main.my

import androidx.lifecycle.MutableLiveData
import com.github.lany192.arch.viewmodel.LifecycleViewModel
import com.github.lany192.box.sample.data.api.ApiCallback
import com.github.lany192.box.sample.data.api.ApiService
import com.github.lany192.box.sample.data.bean.Area
import com.hjq.toast.ToastUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(private val apiService: ApiService) : LifecycleViewModel() {
    val items = MutableLiveData<List<Area>>()

    override fun onLazyLoad() {
        requestCityInfo()
    }

    private fun requestCityInfo() {
        log.i("请求城市数据接口")
        showLoadingView()
        apiService.cityInfo().subscribe(object : ApiCallback<List<Area>> {
            override fun onSuccess(msg: String, areas: List<Area>) {
                items.postValue(areas)
            }

            override fun onFailure(msg: String, code: Int) {
                ToastUtils.show(msg)
            }
        })
    }
}