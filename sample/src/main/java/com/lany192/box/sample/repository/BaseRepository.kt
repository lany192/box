package com.lany192.box.sample.repository

import com.elvishew.xlog.Logger
import com.elvishew.xlog.XLog
import com.lany192.box.sample.data.bean.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException

open class BaseRepository {
    protected var log: Logger.Builder = XLog.tag(javaClass.name)

    fun <T : Any> request(block: suspend () -> ApiResult<T>): Flow<ApiResult<T>> {
        return flow { emit(block()) }
            .flowOn(Dispatchers.IO)
            .catch { e ->
                e.printStackTrace()
                when (this) {
                    is IOException -> {
                        log.e("IO异常")
                    }
                    is IllegalArgumentException -> {
                        log.e("非法参数异常")
                    }
                    else -> {
                        log.e("其他异常")
                    }
                }
            }
    }
}