package com.lany192.box.sample.data.api

import com.lany192.box.sample.data.bean.*
import kotlinx.coroutines.flow.Flow
import retrofit2.http.*

interface ApiService {
    /**
     * 获取省市县数据
     */
    @GET("https://xzwcn.oss-cn-shanghai.aliyuncs.com/config/city.json")
    suspend fun getCityList(): Flow<ApiResult<List<Area>>>

    @GET("/article/list/{page}/json")
    suspend fun getArticleList(@Path("page") page: Int): Flow<ApiResult<PageInfo<Article>>>

    @GET("/article/list/{page}/json")
    suspend fun getHomeArticles(@Path("page") page: Int): Flow<ApiResult<ArticleList>>

    @GET("/user_article/list/{page}/json")
    suspend fun getSquareArticleList(@Path("page") page: Int): Flow<ApiResult<PageInfo<Article>>>

    @GET("/banner/json")
    suspend fun getBanner(): Flow<ApiResult<List<Banner>>>

    @GET("/tree/json")
    suspend fun getSystemType(): Flow<ApiResult<List<SystemParent>>>

    @GET("/article/list/{page}/json")
    suspend fun getSystemTypeDetail(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): Flow<ApiResult<ArticleList>>

    @GET("/navi/json")
    suspend fun getNavigation(): Flow<ApiResult<List<Navigation>>>

    @GET("/project/tree/json")
    suspend fun getProjectType(): Flow<ApiResult<List<SystemParent>>>

    @GET("/wxarticle/chapters/json")
    suspend fun getBlogType(): Flow<ApiResult<List<SystemParent>>>

    @GET("/wxarticle/list/{id}/{page}/json")
    suspend fun getBlogArticle(
        @Path("id") id: Int,
        @Path("page") page: Int
    ): Flow<ApiResult<PageInfo<Article>>>

    @GET("/project/list/{page}/json")
    suspend fun getProjectTypeDetail(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): Flow<ApiResult<ArticleList>>

    @GET("/article/listproject/{page}/json")
    suspend fun getLastedProject(@Path("page") page: Int): Flow<ApiResult<ArticleList>>

    @GET("/friend/json")
    suspend fun getWebsites(): Flow<ApiResult<List<Hot>>>

    @GET("/hotkey/json")
    suspend fun getHot(): Flow<ApiResult<List<Hot>>>

    @FormUrlEncoded
    @POST("/article/query/{page}/json")
    suspend fun searchHot(
        @Path("page") page: Int,
        @Field("k") key: String
    ): Flow<ApiResult<ArticleList>>

    @FormUrlEncoded
    @POST("/user/login")
    suspend fun login(
        @Field("username") userName: String,
        @Field("password") passWord: String
    ): Flow<ApiResult<User>>

    @GET("/user/logout/json")
    suspend fun logOut(): Flow<ApiResult<Any>>

    @FormUrlEncoded
    @POST("/user/register")
    suspend fun register(
        @Field("username") userName: String,
        @Field("password") passWord: String,
        @Field("repassword") rePassWord: String
    ): Flow<ApiResult<User>>

    @GET("/lg/collect/list/{page}/json")
    suspend fun getCollectArticles(@Path("page") page: Int): Flow<ApiResult<ArticleList>>

    @POST("/lg/collect/{id}/json")
    suspend fun collectArticle(@Path("id") id: Int): Flow<ApiResult<ArticleList>>

    @POST("/lg/uncollect_originId/{id}/json")
    suspend fun cancelCollectArticle(@Path("id") id: Int): Flow<ApiResult<ArticleList>>

    @FormUrlEncoded
    @POST("/lg/user_article/add/json")
    suspend fun shareArticle(
        @Field("title") title: String,
        @Field("link") url: String
    ): Flow<ApiResult<String>>
}