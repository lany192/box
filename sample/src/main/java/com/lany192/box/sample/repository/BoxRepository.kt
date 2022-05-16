package com.lany192.box.sample.repository

import com.lany192.box.sample.data.api.ApiService
import com.lany192.box.sample.data.bean.ApiResult
import com.lany192.box.sample.data.bean.Area
import com.lany192.box.sample.data.bean.Article
import com.lany192.box.sample.data.bean.PageInfo
import kotlinx.coroutines.flow.Flow

class BoxRepository(private val service: ApiService) : BaseRepository() {

    fun getCityList(): Flow<ApiResult<List<Area>>> {
        return request { service.getCityList() }
    }

    fun getArticleList(page: Int): Flow<ApiResult<PageInfo<Article>>> {
        return request { service.getArticleList(page) }
    }

    fun getSquareArticleList(page: Int): Flow<ApiResult<PageInfo<Article>>> {
        return request { service.getSquareArticleList(page) }
    }
}