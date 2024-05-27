package com.lany192.box.network.data.api

import com.lany192.box.network.data.model.CommunityRecommend
import com.lany192.box.network.data.model.Daily
import com.lany192.box.network.data.model.Discovery
import com.lany192.box.network.data.model.Follow
import com.lany192.box.network.data.model.HomePageRecommend
import com.lany192.box.network.data.model.PushMessage
import com.lany192.box.network.data.model.VideoBeanForClient
import com.lany192.box.network.data.model.VideoRelated
import com.lany192.box.network.data.model.VideoReplies
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface EyeService {

    /**
     * 首页-发现列表
     */
    @GET("http://baobab.kaiyanapp.com/api/v7/index/tab/discovery")
    suspend fun getDiscovery(@Url url: String): Discovery

    /**
     * 首页-推荐列表
     */
    @GET("http://baobab.kaiyanapp.com/api/v5/index/tab/allRec")
    suspend fun getHomePageRecommend(@Url url: String): HomePageRecommend

    /**
     * 首页-日报列表
     */
    @GET("http://baobab.kaiyanapp.com/api/v5/index/tab/feed")
    suspend fun getDaily(@Url url: String): Daily

    /**
     * 社区-推荐列表
     */
    @GET("http://baobab.kaiyanapp.com/api/v7/community/tab/rec")
    suspend fun getCommunityRecommend(@Url url: String): CommunityRecommend

    /**
     * 社区-关注列表
     */
    @GET("http://baobab.kaiyanapp.com/api/v6/community/tab/follow")
    suspend fun gethFollow(@Url url: String): Follow

    /**
     * 通知-推送列表
     */
    @GET("http://baobab.kaiyanapp.com/api/v3/messages")
    suspend fun getPushMessage(@Url url: String): PushMessage

    /**
     * 搜索-热搜关键词
     */
    @GET("http://baobab.kaiyanapp.com/api/v3/queries/hot")
    suspend fun getHotSearch(): List<String>

    /**
     * 视频详情-视频信息
     */
    @GET("http://baobab.kaiyanapp.com/api/v2/video/{id}")
    suspend fun getVideoBeanForClient(@Path("id") videoId: Long): VideoBeanForClient

    /**
     * 视频详情-推荐列表
     */
    @GET("http://baobab.kaiyanapp.com/api/v4/video/related")
    suspend fun getVideoRelated(@Query("id") videoId: Long): VideoRelated

    /**
     * 视频详情-评论列表
     */
    @GET("http://baobab.kaiyanapp.com/api/v2/replies/video?videoId=")
    suspend fun getVideoReplies(@Url url: String): VideoReplies
}