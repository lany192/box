package cn.smallplants.client.network.api

import com.github.lany192.arch.entity.ApiResult
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PictureApi {
    /**
     * 上传图片
     */
    @FormUrlEncoded
    @POST("/upload/picture")
    suspend fun uploadPicture(
        @Field("picList") json: String
    ): ApiResult<List<Long>>
}