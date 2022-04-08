package com.lany192.box.sample.ui.main.index

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.lany192.arch.viewmodel.LifecycleViewModel
import com.lany192.box.sample.data.bean.Area
import com.lany192.box.sample.data.bean.StateLiveData
import com.lany192.box.sample.repository.BoxRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IndexViewModel @Inject constructor(private val repository: BoxRepository) :
    LifecycleViewModel() {
    val items = MutableLiveData<List<Area>>()
    var sss = StateLiveData<List<Area>>()
    override fun onLazyLoad() {
        requestCityInfo()
    }

    private fun requestCityInfo() {
        log.i("请求城市数据接口")
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCityList(sss)
        }

//        apiService.cityInfo().subscribe(object : ApiCallback<List<Area>> {
//            override fun onSuccess(msg: String, areas: List<Area>) {
//                items.postValue(areas)
//            }
//
//            override fun onFailure(msg: String, code: Int) {
//                ToastUtils.show(msg)
//            }
//        })
    }

}