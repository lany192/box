package com.lany192.box.sample.data.api

import com.lany192.box.sample.data.bean.*
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface ApiService {
    /**
     * 获取省市县数据
     */
    @GET("https://xzwcn.oss-cn-shanghai.aliyuncs.com/config/city.json")
    suspend fun getCityList(): ApiResult<List<Area>>

    @GET("/article/list/{page}/json")
    suspend fun getArticleList(@Path("page") page: Int): ApiResult<PageInfo<Article>>

    @GET("/article/list/{page}/json")
    fun getHomeArticles(@Path("page") page: Int): Observable<ApiResult<ArticleList>>

    @GET("/user_article/list/{page}/json")
    suspend fun getSquareArticleList(@Path("page") page: Int): ApiResult<PageInfo<Article>>


    @GET("/banner/json")
    fun getBanner(): Observable<ApiResult<List<Banner>>>

    @GET("/tree/json")
    fun getSystemType(): Observable<ApiResult<List<SystemParent>>>

    @GET("/article/list/{page}/json")
    fun getSystemTypeDetail(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): Observable<ApiResult<ArticleList>>

    @GET("/navi/json")
    fun getNavigation(): Observable<ApiResult<List<Navigation>>>

    @GET("/project/tree/json")
    fun getProjectType(): Observable<ApiResult<List<SystemParent>>>

    @GET("/wxarticle/chapters/json")
    fun getBlogType(): Observable<ApiResult<List<SystemParent>>>

    @GET("/wxarticle/list/{id}/{page}/json")
    suspend fun getBlogArticle(
        @Path("id") id: Int,
        @Path("page") page: Int
    ): ApiResult<PageInfo<Article>>

    @GET("/project/list/{page}/json")
    fun getProjectTypeDetail(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): Observable<ApiResult<ArticleList>>

    @GET("/article/listproject/{page}/json")
    fun getLastedProject(@Path("page") page: Int): Observable<ApiResult<ArticleList>>

    @GET("/friend/json")
    fun getWebsites(): Observable<ApiResult<List<Hot>>>

    @GET("/hotkey/json")
    fun getHot(): Observable<ApiResult<List<Hot>>>

    @FormUrlEncoded
    @POST("/article/query/{page}/json")
    fun searchHot(
        @Path("page") page: Int,
        @Field("k") key: String
    ): Observable<ApiResult<ArticleList>>

    @FormUrlEncoded
    @POST("/user/login")
    fun login(
        @Field("username") userName: String,
        @Field("password") passWord: String
    ): Observable<ApiResult<User>>

    @GET("/user/logout/json")
    fun logOut(): Observable<ApiResult<Any>>

    @FormUrlEncoded
    @POST("/user/register")
    fun register(
        @Field("username") userName: String,
        @Field("password") passWord: String,
        @Field("repassword") rePassWord: String
    ): Observable<ApiResult<User>>

    @GET("/lg/collect/list/{page}/json")
    fun getCollectArticles(@Path("page") page: Int): Observable<ApiResult<ArticleList>>

    @POST("/lg/collect/{id}/json")
    fun collectArticle(@Path("id") id: Int): Observable<ApiResult<ArticleList>>

    @POST("/lg/uncollect_originId/{id}/json")
    fun cancelCollectArticle(@Path("id") id: Int): Observable<ApiResult<ArticleList>>


    @FormUrlEncoded
    @POST("/lg/user_article/add/json")
    fun shareArticle(
        @Field("title") title: String,
        @Field("link") url: String
    ): Observable<ApiResult<String>>
}