package cn.smallplants.client.network.api

import cn.smallplants.client.model.response.PageInfo
import cn.smallplants.client.model.v4.MultiItem
import com.github.lany192.arch.entity.ApiResult
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface SearchApi {
    /**
     * 搜索热词
     */
    @GET("/search/keyword/hot")
    suspend fun searchHotKeywords(): ApiResult<List<String>>

    /**
     * 搜索用户
     */
    @FormUrlEncoded
    @POST("/search/user")
    suspend fun searchUser(
        //第几页
        @Field("pageNum") pageNum: Int,
        //每页数量
        @Field("pageSize") pageSize: Int,
        //搜索关键字
        @Field("keyword") keyword: String
    ): ApiResult<PageInfo<MultiItem>>

    /**
     * 搜索植物
     */
    @FormUrlEncoded
    @POST("/search/plant")
    suspend fun searchPlant(
        //第几页
        @Field("pageNum") pageNum: Int,
        //每页数量
        @Field("pageSize") pageSize: Int,
        //搜索关键字
        @Field("keyword") keyword: String,
        //搜索排序类型:0发布日期，1最近更新，2最多点赞
        @Field("sort") sort: Int,
    ): ApiResult<PageInfo<MultiItem>>

    /**
     * 搜索长文
     */
    @FormUrlEncoded
    @POST("/search/article")
    suspend fun searchArticle(
        //第几页
        @Field("pageNum") pageNum: Int,
        //每页数量
        @Field("pageSize") pageSize: Int,
        //搜索关键字
        @Field("keyword") keyword: String,
        //搜索排序类型:0发布日期，1最近更新，2最多点赞
        @Field("sort") sort: Int,
    ): ApiResult<PageInfo<MultiItem>>

    /**
     * 搜索美图
     */
    @FormUrlEncoded
    @POST("/search/mettle")
    suspend fun searchMettle(
        //第几页
        @Field("pageNum") pageNum: Int,
        //每页数量
        @Field("pageSize") pageSize: Int,
        //搜索关键字
        @Field("keyword") keyword: String,
        //搜索排序类型:0发布日期，1最近更新，2最多点赞
        @Field("sort") sort: Int,
    ): ApiResult<PageInfo<MultiItem>>
}