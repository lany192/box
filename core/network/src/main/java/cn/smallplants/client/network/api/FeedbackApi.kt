package cn.smallplants.client.network.api

import cn.smallplants.client.model.entity.*
import cn.smallplants.client.model.response.*

import com.github.lany192.arch.entity.ApiResult
import retrofit2.http.*

interface FeedbackApi {

    /**
     * 反馈类型
     */
    @POST("/feedback/types")
    suspend fun feedbackTypes(): ApiResult<List<TypeItem>>

    /**
     * 反馈记录
     */
    @FormUrlEncoded
    @POST("/feedback/records")
    suspend fun feedbackRecords(
        //第几页
        @Field("pageNum") pageNum: Int,
        //每页数量
        @Field("pageSize") pageSize: Int
    ): ApiResult<PageInfo<FeedbackItem>>

    /**
     * 发布反馈
     */
    @FormUrlEncoded
    @POST("/feedback/publish")
    suspend fun feedbackPublish(
        //类型,从类型接口中选择
        @Field("type") type: Int,
        //举报内容
        @Field("content") content: String,
        //联系方式
        @Field("contact") contact: String,
        //图片id，可选项
        @Field("picIds") picIds: List<Long>
    ): ApiResult<Any>
}