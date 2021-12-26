package com.github.lany192.box.sample.ui.main.index.girl

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.github.lany192.box.sample.data.api.ApiService
import com.github.lany192.arch.viewmodel.LifecycleViewModel
import com.github.lany192.box.sample.MockUtils
import java.util.ArrayList

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