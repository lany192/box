package cn.smallplants.client.network.api

import cn.smallplants.client.model.v4.LoginDetail
import com.github.lany192.arch.entity.ApiResult
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("/auth/register")
    suspend fun register(
        //手机号码
        @Field("phone") phone: String,
        //登录密码
        @Field("password") password: String,
        //短信验证码
        @Field("smsCode") smsCode: Int
    ): ApiResult<LoginDetail>

    /**
     * 忘记密码
     */
    @FormUrlEncoded
    @POST("/auth/forget")
    suspend fun forget(
        //手机号码
        @Field("phone") phone: String,
        //登录密码
        @Field("password") password: String,
        //短信验证码
        @Field("smsCode") smsCode: Int
    ): ApiResult<LoginDetail>

    /**
     * 账号密码登录
     */
    @FormUrlEncoded
    @POST("/auth/account")
    suspend fun login(
        //手机号码
        @Field("phone") phone: String,
        @Field("password") password: String
    ): ApiResult<LoginDetail>

    /**
     * 验证码登录
     */
    @FormUrlEncoded
    @POST("/auth/sms")
    suspend fun loginByCode(
        //手机号码
        @Field("phone") phone: String,
        @Field("smsCode") smsCode: Int
    ): ApiResult<LoginDetail>

    /**
     * 第三方账号登录
     */
    @FormUrlEncoded
    @POST("/auth/third")
    suspend fun loginByThird(
        //第三方授权token，如果是苹果登录即identityToken
        @Field("accessToken") accessToken: String,
        //第三方授权OpenId，如果是苹果登录即userID
        @Field("openId") openId: String,
        //授权类型,1密码、2验证码、3一键登录、4qq、5微信、6微博、7苹果、8注册、9忘记密码
        @Field("authType") authType: Int
    ): ApiResult<LoginDetail>
}