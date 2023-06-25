package cn.smallplants.client.network.api

import cn.smallplants.client.model.entity.*
import cn.smallplants.client.model.response.*

import com.github.lany192.arch.entity.ApiResult
import retrofit2.http.*

interface ReportApi {

    /**
     * 举报类型
     */
    @POST("/report/types")
    suspend fun reportTypes(): ApiResult<List<TypeItem>>

    /**
     * 举报记录
     */
    @FormUrlEncoded
    @POST("/report/records")
    suspend fun reportRecords(
        //第几页
        @Field("pageNum") pageNum: Int,
        //每页数量
        @Field("pageSize") pageSize: Int
    ): ApiResult<PageInfo<ReportItem>>

    /**
     * 举报评论
     */
    @FormUrlEncoded
    @POST("/report/comment")
    suspend fun reportComment(
        //评论id
        @Field("id") id: Long,
        //举报类型,从类型接口中选择
        @Field("type") type: Int,
        //举报内容
        @Field("content") content: String,
        //图片id，可选项
        @Field("picIds") picIds: List<Long>
    ): ApiResult<Any>

    /**
     * 举报用户
     */
    @FormUrlEncoded
    @POST("/report/user")
    suspend fun reportUser(
        //被举报人id
        @Field("id") id: Long,
        //举报类型,从类型接口中选择
        @Field("type") type: Int,
        //举报内容
        @Field("content") content: String,
        //图片id，可选项
        @Field("picIds") picIds: List<Long>
    ): ApiResult<Any>

    /**
     * 举报植物
     */
    @FormUrlEncoded
    @POST("/report/plant")
    suspend fun reportPlant(
        //植物id
        @Field("id") id: Long,
        //举报类型,从类型接口中选择
        @Field("type") type: Int,
        //举报内容
        @Field("content") content: String,
        //图片id，可选项
        @Field("picIds") picIds: List<Long>
    ): ApiResult<Any>

    /**
     * 举报长文
     */
    @FormUrlEncoded
    @POST("/report/article")
    suspend fun reportArticle(
        //长文id
        @Field("id") id: Long,
        //举报类型,从类型接口中选择
        @Field("type") type: Int,
        //举报内容
        @Field("content") content: String,
        //图片id，可选项
        @Field("picIds") picIds: List<Long>
    ): ApiResult<Any>

    /**
     * 举报晒图
     */
    @FormUrlEncoded
    @POST("/report/mettle")
    suspend fun reportMettle(
        //晒图id
        @Field("id") id: Long,
        //举报类型,从类型接口中选择
        @Field("type") type: Int,
        //举报内容
        @Field("content") content: String,
        //图片id，可选项
        @Field("picIds") picIds: List<Long>
    ): ApiResult<Any>
}