package cn.smallplants.client.network.api

import cn.smallplants.client.model.entity.*
import cn.smallplants.client.model.response.*

import com.github.lany192.arch.entity.ApiResult
import retrofit2.http.*

interface BlackApi {

    /**
     * 编辑黑名单用户
     */
    @FormUrlEncoded
    @POST("/user/black/edit")
    suspend fun blackEdit(
        //0表示将用户移除黑名单，1表示将用户加入黑名单
        @Field("type") type: Int,
        //目标id
        @Field("id") id: Long
    ): ApiResult<Any>

    /**
     * 我的黑名单
     */
    @FormUrlEncoded
    @POST("/user/black/records")
    suspend fun blackRecords(
        //第几页
        @Field("pageNum") pageNum: Int,
        //每页数量
        @Field("pageSize") pageSize: Int
    ): ApiResult<PageInfo<UserItem>>

}