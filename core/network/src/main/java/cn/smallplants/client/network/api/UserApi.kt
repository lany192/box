package cn.smallplants.client.network.api

import cn.smallplants.client.model.response.PageInfo
import cn.smallplants.client.model.response.UserDetail
import cn.smallplants.client.model.v4.MultiItem

import com.github.lany192.arch.entity.ApiResult
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserApi {

    /**
     * 用户信息
     */
    @POST("/user/userInfo")
    suspend fun getUserInfo(): ApiResult<UserDetail>

    /**
     * 修改个性签名
     */
    @FormUrlEncoded
    @POST("/user/edit/signature")
    suspend fun updateSignature(
        //个性签名
        @Field("signature") signature: String
    ): ApiResult<UserDetail>

    /**
     * 修改昵称
     */
    @FormUrlEncoded
    @POST("/user/edit/nickname")
    suspend fun updateNickName(
        //昵称
        @Field("nickname") nickname: String
    ): ApiResult<UserDetail>

    /**
     * 修改用户所在地址
     */
    @FormUrlEncoded
    @POST("/user/edit/location")
    suspend fun updateLocation(
        //省份id
        @Field("provinceId") provinceId: Long,
        //城市id
        @Field("cityId") cityId: Long,
        //地区id
        @Field("areaId") areaId: Long
    ): ApiResult<UserDetail>

    /**
     * 修改头像
     */
    @FormUrlEncoded
    @POST("/user/edit/avatar")
    suspend fun updateAvatar(@Field("picId") id: Long): ApiResult<UserDetail>

    /**
     * 修改性别
     */
    @FormUrlEncoded
    @POST("/user/edit/sex")
    suspend fun updateSex(
        //性别，1表示男，2表示女，0表示未知
        @Field("sex") sex: Int
    ): ApiResult<UserDetail>

    /**
     * 修改生日
     */
    @FormUrlEncoded
    @POST("/user/edit/birthday")
    suspend fun updateBirthday(
        @Field("year") year: Int,
        @Field("month") month: Int,
        @Field("day") day: Int
    ): ApiResult<UserDetail>

    /**
     * 实名认证
     */
    @FormUrlEncoded
    @POST("/user/real/name")
    suspend fun realName(
        //姓名
        @Field("username") username: String,
        //身认证
        @Field("identityId") identityId: String
    ): ApiResult<UserDetail>

    /**
     * 添加/修改手机
     */
    @FormUrlEncoded
    @POST("/user/edit/phone")
    suspend fun editPhone(
        //旧绑定的手机号码。可选项，如果第一次绑定手机，该值为空
        @Field("oldPhone") oldPhone: String,
        //新手机号码不能为空
        @Field("phone") phone: String,
        //新手机号码收到的短信验证码
        @Field("smsCode") smsCode: Int
    ): ApiResult<UserDetail>

    /**
     * 修改密码
     */
    @FormUrlEncoded
    @POST("/user/edit/password")
    suspend fun updatePassword(
        //旧密码
        @Field("oldPassword") oldPassword: String,
        //新密码
        @Field("newPassword") newPassword: String
    ): ApiResult<UserDetail>

    /**
     * 账号申诉
     */
    @FormUrlEncoded
    @POST("/user/account/appeal")
    suspend fun accountAppeal(
        //旧手机号码
        @Field("oldPhone") oldPhone: String,
        //密码
        @Field("password") password: String,
        //新手机号码
        @Field("newPhone") newPhone: String,
        //新手机号码收到的短信验证码
        @Field("smsCode") code: Int
    ): ApiResult<Void>

    /**
     * 注销账号
     */
    @FormUrlEncoded
    @POST("/user/account/destroy")
    suspend fun accountDestroy(): ApiResult<Void>

    /**
     * 我发布的
     */
    @FormUrlEncoded
    @POST("/user/publish/list")
    suspend fun publishList(
        //排序类型，0发布日期，1最近更新，2最多点赞
        @Field("sort") sort: Int,
        //功能类型：0：植物，1：长文，2：晒图
        @Field("type") type: Int,
        //第几页
        @Field("pageNum") pageNum: Int,
        //每页数量
        @Field("pageSize") pageSize: Int
    ): ApiResult<PageInfo<MultiItem>>

    /**
     * 我喜欢的
     */
    @FormUrlEncoded
    @POST("/user/enjoy/list")
    suspend fun enjoyList(
        //排序类型，0发布日期，1最近更新，2最多点赞
        @Field("sort") sort: Int,
        //第几页
        @Field("pageNum") pageNum: Int,
        //每页数量
        @Field("pageSize") pageSize: Int
    ): ApiResult<PageInfo<MultiItem>>
}