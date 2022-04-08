package com.lany192.box.sample.repository

import com.lany192.box.sample.data.api.ApiService
import com.lany192.box.sample.data.bean.Area
import com.lany192.box.sample.data.bean.ArticleList
import com.lany192.box.sample.data.bean.StateLiveData

class BoxRepository(private val service: ApiService) : BaseRepository() {

    suspend fun getCityList(stateLiveData: StateLiveData<List<Area>>) {
        executeReqWithFlow({ service.getCityList() }, stateLiveData)
    }

    suspend fun getArticleList(page: Int, stateLiveData: StateLiveData<ArticleList>) {
        executeReqWithFlow({ service.getArticleList(page) }, stateLiveData)
    }
}