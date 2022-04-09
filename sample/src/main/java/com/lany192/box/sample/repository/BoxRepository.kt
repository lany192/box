package com.lany192.box.sample.repository

import com.lany192.box.sample.data.api.ApiService
import com.lany192.box.sample.data.api.HttpCallback
import com.lany192.box.sample.data.bean.Area
import com.lany192.box.sample.data.bean.ArticleList

class BoxRepository(private val service: ApiService) : BaseRepository() {

    suspend fun getCityList(callback: HttpCallback<List<Area>>) {
        request({ service.getCityList() }, callback)
    }

    suspend fun getArticleList(page: Int, callback: HttpCallback<ArticleList>) {
        request({ service.getArticleList(page) }, callback)
    }
}