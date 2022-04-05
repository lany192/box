package com.lany192.box.sample.ui.main.index.girl

import android.os.Handler
import com.github.lany192.arch.items.ItemsViewModel
import com.lany192.box.sample.MockUtils
import com.lany192.box.sample.data.api.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GirlViewModel @Inject constructor(private val apiService: ApiService) : ItemsViewModel() {
    var count = 0

    override fun request(refresh: Boolean) {
        if (count < 5) {
            val items: MutableList<Any> = ArrayList()
            for (i in 0..10) {
                items.add(MockUtils.getImageUrl())
            }
            Handler().postDelayed({
                resetItems(items)
                moreLoadFinish()
            }, 1000)
        } else {
            moreLoadEnd()
        }
    }
}