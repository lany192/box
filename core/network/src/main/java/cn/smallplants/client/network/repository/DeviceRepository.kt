package cn.smallplants.client.network.repository

import cn.smallplants.client.model.v4.AppInitInfo
import cn.smallplants.client.model.v4.AppUpdate
import cn.smallplants.client.network.api.ApiService
import com.github.lany192.arch.entity.ApiResult
import com.github.lany192.arch.repository.BaseRepository
import com.github.lany192.arch.utils.DeviceId
import com.github.lany192.arch.utils.PhoneUtils
import kotlinx.coroutines.flow.Flow

class DeviceRepository(private val service: ApiService) : BaseRepository() {
    /**
     * app初始化
     */
    fun deviceInit(): Flow<ApiResult<AppInitInfo>> {
        return request { service.deviceInit() }
    }

    /**
     * 注册设备
     */
    fun deviceRegister(
        //手机型号
        model: String,
        //手机号码
        phone: String
    ): Flow<ApiResult<Any>> {
        return request {
            service.deviceRegister(
                DeviceId.get().deviceId,
                model,
                phone,
                PhoneUtils.getAppVersionCode(),
                PhoneUtils.getOSVersionCode()
            )
        }
    }

    /**
     * 检查升级
     */
    fun deviceUpdate(): Flow<ApiResult<AppUpdate>> {
        return request {
            service.deviceUpdate(
                PhoneUtils.getAppVersionCode(),
                PhoneUtils.getOSVersionCode()
            )
        }
    }

    /**
     * 获取自己的邀请包
     */
    fun deviceInvite(): Flow<ApiResult<String>> {
        return request { service.deviceInvite() }
    }
}