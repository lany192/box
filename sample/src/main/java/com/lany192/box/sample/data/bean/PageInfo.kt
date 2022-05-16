package com.lany192.box.sample.data.bean

import com.google.gson.annotations.SerializedName

open class PageInfo<T> {
    val offset: Int = 0
    val size: Int = 0
    val total: Int = 0
    val pageCount: Int = 0
    val curPage: Int = 0

    @SerializedName("over")
    val over: Boolean = false

    @SerializedName("datas")
    val list: List<T> = ArrayList()
}