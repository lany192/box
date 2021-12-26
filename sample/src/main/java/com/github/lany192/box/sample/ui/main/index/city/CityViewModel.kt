package com.github.lany192.box.sample.ui.main.index.city

import com.github.lany192.arch.items.ListViewModel
import com.github.lany192.box.sample.data.api.ApiCallback
import com.github.lany192.box.sample.data.api.ApiService
import com.github.lany192.box.sample.data.bean.Area
import com.hjq.toast.ToastUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(private val apiService: ApiService) : ListViewModel() {

    override fun request(refresh: Boolean) {
        apiService.cityInfo().subscribe(object : ApiCallback<List<Area>> {
            override fun onSuccess(msg: String, items: List<Area>) {
                if (refresh) {
                    resetItems(items)
                    refreshFinish()
                } else {
                    addItems(items)
                    moreLoadFinish()
                }
            }

            override fun onFailure(msg: String, code: Int) {
                ToastUtils.show(msg)
                finishRequest()
            }
        })
    }
}