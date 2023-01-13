package com.lany192.box.sample.data.api

import com.github.lany192.arch.entity.ApiResult
import com.lany192.box.sample.data.bean.*
import retrofit2.http.*

interface DemoService {
    /**
     * 获取省市县数据
     */
    @GET("https://xzwcn.oss-cn-shanghai.aliyuncs.com/config/city.json")
    suspend fun getCityList(): ApiResult<List<Area>>

    @GET("/article/list/{page}/json")
    suspend fun getArticleList(@Path("page") page: Int): ApiResult<PageInfo<Article>>

    @GET("/article/list/{page}/json")
    suspend fun getHomeArticles(@Path("page") page: Int): ApiResult<PageInfo<Article>>

    @GET("/user_article/list/{page}/json")
    suspend fun getSquareArticleList(@Path("page") page: Int): ApiResult<PageInfo<Article>>

    @GET("/banner/json")
    suspend fun getBanner(): ApiResult<List<Banner>>

    @GET("/tree/json")
    suspend fun getSystemType(): ApiResult<List<SystemParent>>

    @GET("/article/list/{page}/json")
    suspend fun getSystemTypeDetail(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): ApiResult<ArticleList>

    @GET("/navi/json")
    suspend fun getNavigation(): ApiResult<List<Navigation>>

    @GET("/project/tree/json")
    suspend fun getProjectType(): ApiResult<List<SystemParent>>

    @GET("/wxarticle/chapters/json")
    suspend fun getBlogType(): ApiResult<List<SystemParent>>

    @GET("/wxarticle/list/{id}/{page}/json")
    suspend fun getBlogArticle(
        @Path("id") id: Int,
        @Path("page") page: Int
    ): ApiResult<PageInfo<Article>>

    @GET("/project/list/{page}/json")
    suspend fun getProjectTypeDetail(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): ApiResult<ArticleList>

    @GET("/article/listproject/{page}/json")
    suspend fun getLastedProject(@Path("page") page: Int): ApiResult<ArticleList>

    @GET("/friend/json")
    suspend fun getWebsites(): ApiResult<List<Hot>>

    @GET("/hotkey/json")
    suspend fun getHot(): ApiResult<List<Hot>>

    @FormUrlEncoded
    @POST("/article/query/{page}/json")
    suspend fun searchHot(
        @Path("page") page: Int,
        @Field("k") key: String
    ): ApiResult<ArticleList>

    @FormUrlEncoded
    @POST("/user/login")
    suspend fun login(
        @Field("username") userName: String,
        @Field("password") passWord: String
    ): ApiResult<User>

    @GET("/user/logout/json")
    suspend fun logOut(): ApiResult<Any>

    @FormUrlEncoded
    @POST("/user/register")
    suspend fun register(
        @Field("username") userName: String,
        @Field("password") passWord: String,
        @Field("repassword") rePassWord: String
    ): ApiResult<User>

    @GET("/lg/collect/list/{page}/json")
    suspend fun getCollectArticles(@Path("page") page: Int): ApiResult<ArticleList>

    @POST("/lg/collect/{id}/json")
    suspend fun collectArticle(@Path("id") id: Int): ApiResult<ArticleList>

    @POST("/lg/uncollect_originId/{id}/json")
    suspend fun cancelCollectArticle(@Path("id") id: Int): ApiResult<ArticleList>

    @FormUrlEncoded
    @POST("/lg/user_article/add/json")
    suspend fun shareArticle(
        @Field("title") title: String,
        @Field("link") url: String
    ): ApiResult<String>
}