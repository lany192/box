package com.github.lany192.arch.repository

import com.github.lany192.arch.entity.ApiResult
import com.github.lany192.log.XLog
import com.github.lany192.utils.ContextUtils
import com.github.lany192.utils.NetUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion

open class BaseRepository {
    protected var log: XLog = XLog.tag(javaClass.name)

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
            .onCompletion { exception ->
                exception?.let {
                    log.e("onCompletion：$it")
                }
            }
            .catch {
                log.e("请求异常:${it.message}")
                if (ContextUtils.isDebug()) {
                    emit(ApiResult.network(it.message))
                } else {
                    emit(ApiResult.network())
                }
            }
    }
}