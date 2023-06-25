package cn.smallplants.client.network.repository

import cn.smallplants.client.network.api.ApiService
import com.github.lany192.arch.entity.ApiResult
import com.github.lany192.arch.repository.BaseRepository
import kotlinx.coroutines.flow.Flow

class OtherRepository(private val service: ApiService) : BaseRepository() {

//    fun wxAuth(
//        appId: String,
//        secret: String,
//        code: String,
//        grantType: String
//    ): Flow<ApiResult<List<Long>>> {
//        return request { service.wxAuth(JsonUtils.object2json(images)) }
//    }
//
//    fun wxUserInfo(images: List<Picture>): Flow<ApiResult<List<Long>>> {
//        return request { service.wxUserInfo(JsonUtils.object2json(images)) }
//    }

    /**
     * 发送短信
     */
    fun smsCode(
        phone: String,
        //请求类型，见SmsType
        type: Int
    ): Flow<ApiResult<Any>> {
        return request { service.smsCode(phone, type) }
    }
}