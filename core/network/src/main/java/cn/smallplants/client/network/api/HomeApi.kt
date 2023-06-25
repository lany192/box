package cn.smallplants.client.network.api

import cn.smallplants.client.model.response.HomeDetail
import cn.smallplants.client.model.response.PageInfo
import cn.smallplants.client.model.v4.MultiItem

import com.github.lany192.arch.entity.ApiResult
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface HomeApi {
    /**
     * 个人主页基本信息
     */
    @FormUrlEncoded
    @POST("/home/userinfo")
    suspend fun homeUserInfo(
        //目标id
        @Field("id") id: Long
    ): ApiResult<HomeDetail>

    /**
     * 个人主页-用户发布的
     */
    @FormUrlEncoded
    @POST("/home/publish")
    suspend fun homePublish(
        //用户id
        @Field("id") id: Long,
        //排序类型，0发布日期，1最近更新，2最多点赞
        @Field("sort") sort: Int,
        //类型：0植物，1长文，2晒图
        @Field("type") type: Int,
        //第几页
        @Field("pageNum") pageNum: Int,
        //每页数量
        @Field("pageSize") pageSize: Int
    ): ApiResult<PageInfo<MultiItem>>

    /**
     * 个人主页-喜欢的植物
     */
    @FormUrlEncoded
    @POST("/home/enjoy")
    suspend fun homeEnjoy(
        //用户id
        @Field("id") id: Long,
        //排序类型，0发布日期，1最近更新，2最多点赞
        @Field("sort") sort: Int,
        //第几页
        @Field("pageNum") pageNum: Int,
        //每页数量
        @Field("pageSize") pageSize: Int
    ): ApiResult<PageInfo<MultiItem>>
}