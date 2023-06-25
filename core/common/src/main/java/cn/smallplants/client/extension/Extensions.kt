package cn.smallplants.client.extension

import com.github.lany192.arch.entity.ApiResult

fun <T> ApiResult<T>.success(): Boolean {
    return this.code == 200
}
