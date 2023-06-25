package cn.smallplants.client.network.api

import cn.smallplants.client.model.entity.*
import cn.smallplants.client.model.response.*
import cn.smallplants.client.model.v4.MettleDetail
import cn.smallplants.client.model.v4.MettleItem
import com.github.lany192.arch.entity.ApiResult
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MettleApi {
    /**
     * 创建美图
     */
    @FormUrlEncoded
    @POST("/mettle/build")
    suspend fun mettleBuild(
        //晒图标题
        @Field("title") title: String,
        //晒图内容
        @Field("content") content: String,
        //要关联的图片ids
        @Field("imageIds") imageIds: List<Long>
    ): ApiResult<Long>

    /**
     * 发布美图
     */
    @POST("/mettle/publish")
    suspend fun mettlePublish(
        @Field("id") mettleId: Long
    ): ApiResult<Any>

    /**
     * 编辑美图
     */
    @FormUrlEncoded
    @POST("/mettle/edit")
    suspend fun mettleEdit(
        //晒图ID
        @Field("id") mettleId: Long,
        //晒图标题
        @Field("title") title: String,
        //晒图标题
        @Field("content") content: String,
        //要关联的图片ids
        @Field("imageIds") imageIds: List<Long>
    ): ApiResult<Long>

    /**
     * 晒图详情
     */
    @POST("/mettle/detail")
    suspend fun mettleDetail(
        //晒图ID
        @Field("id") id: Long
    ): ApiResult<MettleDetail>

    /**
     * 删除美图
     */
    @POST("/mettle/delete")
    suspend fun mettleDelete(
        //美图ID
        @Field("id") id: Long
    ): ApiResult<Any>

    /**
     * 美图草稿
     */
    @POST("/mettle/drafts")
    suspend fun mettleDrafts(
        //第几页
        @Field("pageNum") pageNum: Int,
        //每页数量
        @Field("pageSize") pageSize: Int
    ): ApiResult<PageInfo<MettleItem>>
}