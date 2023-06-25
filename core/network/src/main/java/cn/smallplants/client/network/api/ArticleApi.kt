package cn.smallplants.client.network.api

import cn.smallplants.client.model.entity.*
import cn.smallplants.client.model.response.*
import cn.smallplants.client.model.v4.ArticleDetail
import cn.smallplants.client.model.v4.ArticleItem
import com.github.lany192.arch.entity.ApiResult
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ArticleApi {
    /**
     * 创建文章
     */
    @FormUrlEncoded
    @POST("/article/build")
    suspend fun articleBuild(
        //文章标题
        @Field("title") title: String,
        //文章内容
        @Field("content") content: String,
        //封面图片ID
        @Field("coverId") coverId: String,
        //是否发布
        @Field("online") online: Boolean,
        //要关联的植记ids
        @Field("plantIds") plantIds: List<Long>,
        //要关联的商品ids
        @Field("goodsIds") goodsIds: List<Long>
    ): ApiResult<Long>

    /**
     * 发布文章
     */
    @POST("/article/publish")
    suspend fun articlePublish(
        @Field("id") articleId: Long
    ): ApiResult<Any>

    /**
     * 编辑文章
     */
    @FormUrlEncoded
    @POST("/article/edit")
    suspend fun articleEdit(
        //文章标题
        @Field("id") articleId: Long,
        //文章标题
        @Field("title") title: String,
        //文章内容
        @Field("content") content: String,
        //封面图片ID
        @Field("coverId") coverId: String,
        //是否发布
        @Field("online") online: Boolean,
        //要关联的植记ids
        @Field("plantIds") plantIds: List<Long>,
        //要关联的商品ids
        @Field("goodsIds") goodsIds: List<Long>
    ): ApiResult<Long>

    /**
     * 文章详情
     */
    @POST("/article/detail")
    suspend fun articleDetail(
        //文章ID
        @Field("id") id: Long
    ): ApiResult<ArticleDetail>

    /**
     * 删除文章
     */
    @POST("/article/delete")
    suspend fun articleDelete(
        //文章ID
        @Field("id") id: Long
    ): ApiResult<Any>

    /**
     * 文章草稿
     */
    @POST("/article/drafts")
    suspend fun articleDrafts(
        //第几页
        @Field("pageNum") pageNum: Int,
        //每页数量
        @Field("pageSize") pageSize: Int
    ): ApiResult<PageInfo<ArticleItem>>
}