package com.lany192.box.sample.ui.main.discover

import android.os.Handler
import android.os.Looper
import com.github.lany192.arch.items.ItemsViewModel
import com.lany192.box.network.data.bean.Category
import com.lany192.box.sample.MockUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor() : ItemsViewModel() {
    var count = 0
    override fun request(refresh: Boolean) {
        if (refresh) {
            count = 0
        }
        if (count < 5) {
            val images: MutableList<Any?> = ArrayList()
            if (refresh) {
                for (i in 0..3) {
                    images.add(Category(i, "分类$i", MockUtils.getImageUrl()))
                }
            }
            for (i in 0..4) {
                images.add(MockUtils.getImageUrl())
            }
            Handler(Looper.myLooper()!!).postDelayed({
                if (refresh) {
                    resetItems(images)
                    refreshFinish()
                } else {
                    addItems(images)
                    moreLoadFinish()
                }
                count++
            }, 3000)
        } else {
            moreLoadEnd()
        }
    }
}