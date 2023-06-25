package cn.smallplants.client.network.api

import cn.smallplants.client.model.entity.*
import cn.smallplants.client.model.response.*
import cn.smallplants.client.model.v4.PlantDetailListPage
import com.github.lany192.arch.entity.ApiResult
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PlantApi {
    /**
     * 创建成长记
     */
    @FormUrlEncoded
    @POST("/plant/build")
    suspend fun plantBuild(
        //植物标题
        @Field("title") title: String,
        //植物描述
        @Field("description") description: String,
        //要关联的图片ids
        @Field("picIds") picIds: List<Long>
    ): ApiResult<Long>

    /**
     * 编辑植物
     */
    @FormUrlEncoded
    @POST("/plant/edit")
    suspend fun plantEdit(
        //成长记ID
        @Field("id") plantId: Long,
        //成长记标题
        @Field("title") title: String,
        //成长记描述
        @Field("description") description: String
    ): ApiResult<EditPlantInfo>

    /**
     * 植物详情
     */
    @FormUrlEncoded
    @POST("/plant/detail")
    suspend fun plantDetail(
        //成长记ID
        @Field("id") id: Long,
        //第几页
        @Field("pageNum") pageNum: Int,
        //每页数量
        @Field("pageSize") pageSize: Int
    ): ApiResult<PlantDetailListPage>

    /**
     * 删除某植物
     */
    @FormUrlEncoded
    @POST("/plant/delete")
    suspend fun plantDelete(
        @Field("id") id: Long
    ): ApiResult<Any>

    /**
     * 创建日记
     */
    @FormUrlEncoded
    @POST("/plant/diary/build")
    suspend fun diaryBuild(
        //植物ID
        @Field("plantId") plantId: Long,
        //日记时间，时间戳
        @Field("recordTime") recordTime: Long,
        //植物描述
        @Field("description") description: String,
        //是否重置植物封面
        @Field("resetCover") resetCover: Boolean,
        //要关联的图片ids
        @Field("picIds") picIds: List<Long>,
        //需要绑定的商品ids
        @Field("goodsIds") goodsIds: List<Long>
    ): ApiResult<Long>

    /**
     * 编辑日记
     */
    @FormUrlEncoded
    @POST("/plant/diary/edit")
    suspend fun diaryEdit(
        //植物ID
        @Field("plantId") plantId: Long,
        //日记id
        @Field("diaryId") diaryId: Long,
        //植物描述
        @Field("description") description: String,
        //日记时间，时间戳
        @Field("recordTime") recordTime: Long,
        //是否重置植物封面
        @Field("resetCover") resetCover: Boolean,
        //要关联的图片ids
        @Field("picIds") picIds: List<Long>,
        //没有被删除的图片id集
        @Field("oldImageIds") oldImageIds: List<Long>,
        //需要绑定的商品ids
        @Field("goodsIds") goodsIds: List<Long>
    ): ApiResult<Long>

    /**
     * 日记信息详情
     */
    @FormUrlEncoded
    @POST("/plant/diary/detail")
    suspend fun diaryDetail(
        @Field("id") id: Long
    ): ApiResult<DiaryDetail>

    /**
     * 创建日记商品
     */
    @FormUrlEncoded
    @POST("/plant/goods/build")
    suspend fun goodsBuild(
        //商品描述
        @Field("title") title: String,
        //商品淘口令
        @Field("code") code: String
    ): ApiResult<Any>

    /**
     * 删除商品
     */
    @FormUrlEncoded
    @POST("/plant/goods/delete")
    suspend fun goodsDelete(
        @Field("id") id: Long
    ): ApiResult<Any>

    /**
     * 日记商品列表
     */
    @FormUrlEncoded
    @POST("/plant/goods/list")
    suspend fun goodsList(
        @Field("pageNum") pageNum: Int,
        @Field("pageSize") pageSize: Int
    ): ApiResult<PageInfo<GoodsItem>>

    /**
     * 植物的所有图片
     */
    @FormUrlEncoded
    @POST("/plant/images")
    suspend fun getPlantImages(
        @Field("id") plantId: Long
    ): ApiResult<List<Picture>>
}