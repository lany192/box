package com.lany192.box.sample.data.bean

import com.google.gson.annotations.SerializedName

class ApiResult<T> {
    @SerializedName(value = "code", alternate = ["errorCode"])
    var code = 0

    @SerializedName(value = "msg", alternate = ["errorMsg"])
    var msg: String? = null

    @SerializedName(value = "data", alternate = ["result"])
    var data: T? = null
}