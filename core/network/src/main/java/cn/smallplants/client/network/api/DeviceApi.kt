package cn.smallplants.client.network.api

import cn.smallplants.client.model.entity.*
import cn.smallplants.client.model.response.*
import cn.smallplants.client.model.v4.AppInitInfo
import cn.smallplants.client.model.v4.AppUpdate

import com.github.lany192.arch.entity.ApiResult
import retrofit2.http.*

interface DeviceApi {
    /**
     * app初始化
     */
    @FormUrlEncoded
    @POST("/device/init")
    suspend fun deviceInit(): ApiResult<AppInitInfo>

    /**
     * 注册设备
     */
    @FormUrlEncoded
    @POST("/device/register")
    suspend fun deviceRegister(
        //申请注册的设备id
        @Field("deviceId") deviceId: String,
        //手机型号
        @Field("model") model: String,
        //手机号码
        @Field("phone") phone: String,
        //app版本号
        @Field("appVersionCode") appVersionCode: Int,
        //系统版本号
        @Field("osVersionCode") osVersionCode: Int
    ): ApiResult<Any>

    /**
     * 检查升级
     */
    @FormUrlEncoded
    @POST("/device/update")
    suspend fun deviceUpdate(
        //app版本号
        @Field("appVersionCode") appVersionCode: Int,
        //系统版本号
        @Field("osVersionCode") osVersionCode: Int
    ): ApiResult<AppUpdate>

    /**
     * 获取自己的邀请包
     */
    @FormUrlEncoded
    @POST("/device/invite")
    suspend fun deviceInvite(): ApiResult<String>
}