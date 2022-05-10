package com.lany192.box.sample.data.bean

import com.squareup.moshi.Json

class ApiResult<T> {
    @Json(name = "errorCode")
    var code = 0

    @Json(name = "errorMsg")
    var msg: String? = null

    @Json(name = "result")
    var data: T? = null
}