package com.lany192.box.sample.repository

import com.elvishew.xlog.Logger
import com.elvishew.xlog.XLog
import com.github.lany192.utils.NetUtils
import com.lany192.box.sample.data.bean.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

open class BaseRepository {
    protected var log: Logger.Builder = XLog.tag(javaClass.name)

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
                emit(ApiResult.network())
            }
    }
}