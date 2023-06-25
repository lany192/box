package cn.smallplants.client.network.api

import cn.smallplants.client.model.entity.*
import cn.smallplants.client.model.response.*
import cn.smallplants.client.model.v4.Location
import cn.smallplants.client.model.v4.MultiItem
import com.github.lany192.arch.entity.ApiResult
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface IndexApi {
    /**
     * 首页-爱看
     */
    @FormUrlEncoded
    @POST("/index/enjoy")
    suspend fun indexEnjoy(
        //功能类型：0：植物，1：长文，2：晒图
        @Field("type") type: Int,
        //第几页
        @Field("pageNum") pageNum: Int,
        //每页数量
        @Field("pageSize") pageSize: Int
    ): ApiResult<PageInfo<MultiItem>>

    /**
     * 首页-推荐
     */
    @FormUrlEncoded
    @POST("/index/recommend")
    suspend fun indexRecommend(
        //第几页
        @Field("pageNum") pageNum: Int,
        //每页数量
        @Field("pageSize") pageSize: Int
    ): ApiResult<PageInfo<MultiItem>>

    /**
     * 首页-关注
     */
    @FormUrlEncoded
    @POST("/index/attention")
    suspend fun indexAttention(
        //第几页
        @Field("pageNum") pageNum: Int,
        //每页数量
        @Field("pageSize") pageSize: Int
    ): ApiResult<PageInfo<MultiItem>>

    /**
     * 首页-同城
     */
    @FormUrlEncoded
    @POST("/index/location")
    suspend fun indexLocation(
        //第几页
        @Field("pageNum") pageNum: Int,
        //每页数量
        @Field("pageSize") pageSize: Int
    ): ApiResult<Location>
}