package com.github.lany192.arch.repository

import com.github.lany192.arch.BuildConfig
import com.github.lany192.arch.entity.ApiResult
import com.github.lany192.utils.LogUtils
import com.github.lany192.utils.NetUtils
import com.github.lany192.utils.XLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

open class BaseRepository {
    protected var log: XLog = LogUtils.tag(javaClass.name)

    /**
     * 执行接口请求，Flow方式
     */
    fun <T> request(block: suspend () -> ApiResult<T>): Flow<ApiResult<T>> {
        return flow {
            if (NetUtils.isAvailable()) {
                emit(block())
            } else {
                log.e("请求异常:无网络")
                emit(ApiResult.network())
            }
        }.flowOn(Dispatchers.IO)
            .catch {
                log.e("请求异常:", it)
                if (BuildConfig.DEBUG) {
                    emit(ApiResult.network(it.message))
                } else {
                    emit(ApiResult.network())
                }
            }
    }
}