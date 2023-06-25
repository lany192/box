package cn.smallplants.client.network.repository

import cn.smallplants.client.model.v4.LoginDetail
import cn.smallplants.client.network.api.ApiService
import com.github.lany192.arch.entity.ApiResult
import com.github.lany192.arch.repository.BaseRepository
import kotlinx.coroutines.flow.Flow

class AuthRepository(private val service: ApiService) : BaseRepository() {
    /**
     * 注册
     */
    fun register(
        //手机号码
        phone: String,
        //登录密码
        password: String,
        //短信验证码
        smsCode: Int
    ): Flow<ApiResult<LoginDetail>> {
        return request { service.register(phone, password, smsCode) }
    }

    /**
     * 忘记密码
     */
    fun forget(
        //手机号码
        phone: String,
        //登录密码
        password: String,
        //短信验证码
        smsCode: Int
    ): Flow<ApiResult<LoginDetail>> {
        return request { service.forget(phone, password, smsCode) }
    }

    /**
     * 账号密码登录
     */
    fun login(
        //手机号码
        phone: String,
        //登录密码
        password: String
    ): Flow<ApiResult<LoginDetail>> {
        return request { service.login(phone, password) }
    }

    /**
     * 验证码登录
     */
    fun loginByCode(
        //手机号码
        phone: String,
        //短信验证码
        smsCode: Int
    ): Flow<ApiResult<LoginDetail>> {
        return request { service.loginByCode(phone, smsCode) }
    }

    /**
     * 第三方账号登录
     */
    fun loginByThird(
        //第三方授权token，如果是苹果登录即identityToken
        accessToken: String,
        //第三方授权OpenId，如果是苹果登录即userID
        openId: String,
        //授权类型,1密码、2验证码、3一键登录、4qq、5微信、6微博、7苹果、8注册、9忘记密码
        authType: Int
    ): Flow<ApiResult<LoginDetail>> {
        return request { service.loginByThird(accessToken, openId, authType) }
    }
}