package cn.smallplants.client.network.api

import cn.smallplants.client.model.entity.*
import cn.smallplants.client.model.response.*

import com.github.lany192.arch.entity.ApiResult
import retrofit2.http.*

interface UpvoteApi {

    /**
     * 点赞评论
     */
    @FormUrlEncoded
    @POST("/upvote/comment")
    suspend fun upvoteComment(
        //评论id
        @Field("commentId") commentId: Long,
        //false表示取消点赞，true表示添加点赞
        @Field("upvote") upvote: Boolean
    ): ApiResult<LikeResult>

    /**
     * 点赞回复
     */
    @FormUrlEncoded
    @POST("/upvote/reply")
    suspend fun upvoteReply(
        //评论id
        @Field("commentId") commentId: Long,
        //回复id
        @Field("replyId") replyId: Long,
        //false表示取消点赞，true表示添加点赞
        @Field("upvote") upvote: Boolean
    ): ApiResult<LikeResult>

}