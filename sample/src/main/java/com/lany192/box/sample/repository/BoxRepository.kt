package com.lany192.box.sample.repository

import com.lany192.box.sample.data.api.ApiService
import com.lany192.box.sample.data.bean.ApiResult
import com.lany192.box.sample.data.bean.Area
import com.lany192.box.sample.data.bean.ArticleList
import kotlinx.coroutines.flow.Flow

class BoxRepository(private val service: ApiService) : BaseRepository() {

    fun getCityList(): Flow<ApiResult<List<Area>>> {
        return request { service.getCityList() }
    }

    fun getArticleList(page: Int): Flow<ApiResult<ArticleList>> {
        log.i("页面：$page")
        return request { service.getArticleList(page) }
    }
}