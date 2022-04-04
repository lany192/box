package com.lany192.box.sample.ui.main.index.girl

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import com.github.lany192.arch.viewmodel.LifecycleViewModel
import com.lany192.box.sample.MockUtils
import com.lany192.box.sample.data.api.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GirlViewModel @Inject constructor(private val apiService: ApiService) : LifecycleViewModel() {

    val items = MutableLiveData<List<String>>()

    override fun onLazyLoad() {
        requestCityInfo()
    }

    private fun requestCityInfo() {
        val images: MutableList<String> = ArrayList()
        for (i in 0..49) {
            images.add(MockUtils.getImageUrl())
        }
        Handler().postDelayed({ items.postValue(images) }, 1000)
    }
}