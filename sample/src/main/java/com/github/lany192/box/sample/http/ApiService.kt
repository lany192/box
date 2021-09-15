package com.github.lany192.box.sample.http

import com.github.lany192.box.sample.bean.Area
import com.github.lany192.box.sample.bean.Result
import retrofit2.http.GET

interface ApiService {
    /**
     * 获取省市县数据
     */
    @GET("https://xzwcn.oss-cn-shanghai.aliyuncs.com/config/city.json")
    suspend fun cityInfo(): Result<List<Area>>
}