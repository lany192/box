package com.github.lany192.box.sample.bean

class Result<T> {
    var code = 0
    var msg: String? = null

    var data: T? = null
        private set

    fun setData(data: T) {
        this.data = data
    }

    val isSuccess: Boolean
        get() = code == 200
}