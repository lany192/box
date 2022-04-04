package com.lany192.box.sample.data.api

import com.lany192.box.sample.data.bean.*
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface ApiService {
    /**
     * 获取省市县数据
     */
    @GET("https://xzwcn.oss-cn-shanghai.aliyuncs.com/config/city.json")
    fun cityInfo(): Observable<Result<List<Area>>>

    @GET("/article/list/{page}/json")
    fun getHomeArticles(@Path("page") page: Int): Observable<Result<ArticleList>>

    @GET("/banner/json")
    fun getBanner(): Observable<Result<List<Banner>>>

    @GET("/tree/json")
    fun getSystemType(): Observable<Result<List<SystemParent>>>

    @GET("/article/list/{page}/json")
    fun getSystemTypeDetail(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): Observable<Result<ArticleList>>

    @GET("/navi/json")
    fun getNavigation(): Observable<Result<List<Navigation>>>

    @GET("/project/tree/json")
    fun getProjectType(): Observable<Result<List<SystemParent>>>

    @GET("/wxarticle/chapters/json")
    fun getBlogType(): Observable<Result<List<SystemParent>>>

    @GET("/wxarticle/list/{id}/{page}/json")
    fun getBlogArticle(
        @Path("id") id: Int,
        @Path("page") page: Int
    ): Observable<Result<ArticleList>>

    @GET("/project/list/{page}/json")
    fun getProjectTypeDetail(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): Observable<Result<ArticleList>>

    @GET("/article/listproject/{page}/json")
    fun getLastedProject(@Path("page") page: Int): Observable<Result<ArticleList>>

    @GET("/friend/json")
    fun getWebsites(): Observable<Result<List<Hot>>>

    @GET("/hotkey/json")
    fun getHot(): Observable<Result<List<Hot>>>

    @FormUrlEncoded
    @POST("/article/query/{page}/json")
    fun searchHot(@Path("page") page: Int, @Field("k") key: String): Observable<Result<ArticleList>>

    @FormUrlEncoded
    @POST("/user/login")
    fun login(
        @Field("username") userName: String,
        @Field("password") passWord: String
    ): Observable<Result<User>>

    @GET("/user/logout/json")
    fun logOut(): Observable<Result<Any>>

    @FormUrlEncoded
    @POST("/user/register")
    fun register(
        @Field("username") userName: String,
        @Field("password") passWord: String,
        @Field("repassword") rePassWord: String
    ): Observable<Result<User>>

    @GET("/lg/collect/list/{page}/json")
    fun getCollectArticles(@Path("page") page: Int): Observable<Result<ArticleList>>

    @POST("/lg/collect/{id}/json")
    fun collectArticle(@Path("id") id: Int): Observable<Result<ArticleList>>

    @POST("/lg/uncollect_originId/{id}/json")
    fun cancelCollectArticle(@Path("id") id: Int): Observable<Result<ArticleList>>

    @GET("/user_article/list/{page}/json")
    fun getSquareArticleList(@Path("page") page: Int): Observable<Result<ArticleList>>

    @FormUrlEncoded
    @POST("/lg/user_article/add/json")
    fun shareArticle(
        @Field("title") title: String,
        @Field("link") url: String
    ): Observable<Result<String>>
}