package com.github.lany192.box.sample.http;

import com.github.lany192.box.sample.bean.Area;
import com.github.lany192.box.sample.bean.Result;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface ApiService {
    /**
     * 获取省市县数据
     */
    @GET("json/city.json")
    Observable<Result<List<Area>>> getCityInfo();
}
