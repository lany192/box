package cn.smallplants.client.network.api

import cn.smallplants.client.model.response.AttentionResult
import cn.smallplants.client.model.response.PageInfo
import cn.smallplants.client.model.response.UserItem
import com.github.lany192.arch.entity.ApiResult
import retrofit2.http.*

interface AttentionApi {
    /**
     * 添加/取消 关注用户
     */
    @FormUrlEncoded
    @POST("/attention/user")
    suspend fun attentionUser(
        //0表示取消关注，1表示关注用户
        @Field("type") type: Int,
        //目标用户id
        @Field("id") id: Long
    ): ApiResult<AttentionResult>

    /**
     * 已关注用户列表
     */
    @FormUrlEncoded
    @POST("/attention/list")
    suspend fun attentionUserList(
        //是否是粉丝列表
        @Field("fans") fans: Boolean,
        //用户id
        @Field("ownerId") ownerId: Long,
        //第几页
        @Field("pageNum") pageNum: Int,
        //每页数量
        @Field("pageSize") pageSize: Int
    ): ApiResult<PageInfo<UserItem>>
}