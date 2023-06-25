package cn.smallplants.client.network.api

import cn.smallplants.client.model.response.*
import com.github.lany192.arch.entity.ApiResult
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MessageApi {

    /**
     * 未读消息
     */
    @POST("/message/unread/info")
    suspend fun getUnreadCount(): ApiResult<MsgUnread>

    /**
     * 清除未读
     */
    @POST("/message/unread/clear")
    suspend fun clearUnread(): ApiResult<Any>

    /**
     * 互动消息
     */
    @FormUrlEncoded
    @POST("/message/mixture")
    suspend fun messageMixture(
        //筛选类型，0全部，1点赞，2@我的，3评论
        @Field("type") type: Int,
        //第几页
        @Field("pageNum") pageNum: Int,
        //每页数量
        @Field("pageSize") pageSize: Int
    ): ApiResult<PageInfo<MsgMixtureItem>>

    /**
     * 消息-关注
     */
    @FormUrlEncoded
    @POST("/msg/attention")
    suspend fun messageAttention(
        //第几页
        @Field("pageNum") pageNum: Int,
        //每页数量
        @Field("pageSize") pageSize: Int
    ): ApiResult<PageInfo<MsgAttentionItem>>

    /**
     * 评论消息列表
     */
    @FormUrlEncoded
    @POST("/message/comment")
    suspend fun messageComment(
        //第几页
        @Field("pageNum") pageNum: Int,
        //每页数量
        @Field("pageSize") pageSize: Int
    ): ApiResult<PageInfo<MsgCommentItem>>

    /**
     * 消息-系统公告
     */
    @FormUrlEncoded
    @POST("/message/notice")
    suspend fun messageNotice(
        //第几页
        @Field("pageNum") pageNum: Int,
        //每页数量
        @Field("pageSize") pageSize: Int
    ): ApiResult<PageInfo<MsgNoticeItem>>

    /**
     * 消息-系统公告
     */
    @FormUrlEncoded
    @POST("/message/system")
    suspend fun messageSystem(
        //第几页
        @Field("pageNum") pageNum: Int,
        //每页数量
        @Field("pageSize") pageSize: Int
    ): ApiResult<PageInfo<MsgSystemItem>>
}