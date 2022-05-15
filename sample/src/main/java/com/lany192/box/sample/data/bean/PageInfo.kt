package com.lany192.box.sample.data.bean

import com.google.gson.annotations.SerializedName

open class PageInfo<T> {
    /**
     * 是否有下一页
     */
    @SerializedName("hasNext")
    var hasNext = false

    @SerializedName("page")
    var page = 0

    @SerializedName("page_size")
    var pageSize = 0

    @SerializedName("list")
    var list: List<T> = ArrayList()
}