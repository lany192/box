package cn.smallplants.client.network.api

import cn.smallplants.client.model.entity.*
import cn.smallplants.client.model.response.*
import com.github.lany192.arch.entity.ApiResult
import retrofit2.http.*

interface OtherApi {
//    /**
//     * 获取微信授权
//     */
//    @GET("https://api.weixin.qq.com/sns/oauth2/access_token")
//    suspend fun wxAuth(
//        @Query("appid") appId: String,
//        @Query("secret") secret: String,
//        @Query("code") code: String,
//        @Query("grant_type") grantType: String
//    ): Call<WeiXinToken>
//
//    /**
//     * 获取微信用户信息
//     */
//    @GET("https://api.weixin.qq.com/sns/userinfo")
//    suspend fun wxUserInfo(
//        @Query("access_token") accessToken: String,
//        @Query("openid") openId: String
//    ): Call<WeChatUserInfo>

    /**
     * 获取手机验证码
     */
    @FormUrlEncoded
    @POST("/other/sms/send")
    suspend fun smsCode(
        @Field("phone") phone: String,
        //请求类型，见SmsType
        @Field("type") type: Int
    ): ApiResult<Any>
}