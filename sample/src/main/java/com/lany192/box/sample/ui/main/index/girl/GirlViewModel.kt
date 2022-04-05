package com.lany192.box.sample.ui.main.index.girl

import android.os.Handler
import android.os.Looper
import com.github.lany192.arch.items.ItemsViewModel
import com.lany192.box.sample.MockUtils
import com.lany192.box.sample.data.api.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GirlViewModel @Inject constructor(private val apiService: ApiService) : ItemsViewModel() {
    var count = 0

    override fun request(refresh: Boolean) {
        if (refresh) {
            count = 0
        }
        if (count < 5) {
            val items: MutableList<Any> = ArrayList()
            for (i in 0..5) {
                items.add(MockUtils.getImageUrl())
            }
            Handler(Looper.getMainLooper()).postDelayed({
                if (refresh) {
                    resetItems(items)
                    refreshFinish()
                } else {
                    addItems(items)
                    moreLoadFinish()
                }
                count++
            }, 1000)
        } else {
            moreLoadEnd()
        }
    }
}