package cn.smallplants.client.network.api

import cn.smallplants.client.model.entity.*
import cn.smallplants.client.model.response.*
import cn.smallplants.client.model.v4.CommentListPage

import com.github.lany192.arch.entity.ApiResult
import retrofit2.http.*

interface CommentApi {

    /**
     * 评论列表
     */
    @FormUrlEncoded
    @POST("/comment/list")
    suspend fun commentsList(
        //类型：0植物，1长文，2晒图
        @Field("type") type: Int,
        //目标id
        @Field("id") id: Long,
        //需要置顶的评论id，可选项
        @Field("topCommentId") topCommentId: Long,
        //第几页
        @Field("pageNum") pageNum: Int,
        //每页数量
        @Field("pageSize") pageSize: Int
    ): ApiResult<CommentListPage>

    /**
     * 评论发布
     */
    @FormUrlEncoded
    @POST("/comment/publish")
    suspend fun commentPublish(
        //类型：0植物，1长文，2晒图
        @Field("type") type: Int,
        //目标id
        @Field("id") id: Long,
        //评论内容
        @Field("content") content: String,
        //图片ids，可选项
        @Field("picIds") picIds: List<Long>
    ): ApiResult<Any>

    /**
     * 评论详情
     */
    @FormUrlEncoded
    @POST("/comment/detail")
    suspend fun commentDetail(
        //目标id
        @Field("id") id: Long,
        //需要置顶的评论id，可选项
        @Field("topReplyId") topReplyId: Long,
        //第几页
        @Field("pageNum") pageNum: Int,
        //每页数量
        @Field("pageSize") pageSize: Int
    ): ApiResult<Any>

    /**
     * 删除评论
     */
    @FormUrlEncoded
    @POST("/comment/delete")
    suspend fun commentDelete(
        //目标id
        @Field("id") id: Long
    ): ApiResult<DeleteInfo>

    /**
     * 发布回复
     */
    @FormUrlEncoded
    @POST("/comment/reply/publish")
    suspend fun publishReply(
        //评论id
        @Field("commentId") commentId: Long,
        //评论内容
        @Field("content") content: String,
        //被回复的回复id（选填）
        @Field("toReplyId") toReplyId: Long
    ): ApiResult<ReplyDetail>

    /**
     * 删除回复
     */
    @FormUrlEncoded
    @POST("/comment/reply/delete")
    suspend fun deleteReply(
        //目标id
        @Field("id") id: Long
    ): ApiResult<DeleteInfo>

}