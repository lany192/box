package com.lany192.box.sample.repository

import com.elvishew.xlog.Logger
import com.elvishew.xlog.XLog
import com.github.lany192.utils.JsonUtils
import com.lany192.box.sample.data.bean.ApiResult
import com.lany192.box.sample.data.bean.StateLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*


/**
 * 具体流程请参考：https://juejin.cn/post/6961055228787425288
 */
open class BaseRepository {
    @JvmField
    protected var log: Logger.Builder = XLog.tag(javaClass.name)

    suspend fun <T : Any> executeReqWithFlow(
        block: suspend () -> ApiResult<T>,
        stateLiveData: StateLiveData<T>
    ) {
        var result = ApiResult<T>()
        flow {
            val respResult = block.invoke()
            result = respResult
            stateLiveData.postValue(result)
            emit(respResult)
        }
            .flowOn(Dispatchers.IO)
            .onStart {
                log.i("测试executeReqWithFlow:onStart")
                stateLiveData.postValue(result)
            }
            .onEmpty {
                log.i("测试executeReqWithFlow:onEmpty")
//                baseResp.dataState = DataState.STATE_EMPTY
                stateLiveData.postValue(result)
            }
            .catch { exception ->
                run {
                    log.e(exception)
                    log.i("测试executeReqWithFlow:code  ${result.code}")
                    exception.printStackTrace()
//                    baseResp.dataState = DataState.STATE_ERROR
//                    baseResp.error = exception
                    stateLiveData.postValue(result)
                }
            }
            .collect {
                log.i("测试executeReqWithFlow: collect")
                log.json(JsonUtils.object2json(it))
                stateLiveData.postValue(result)
            }
    }
}