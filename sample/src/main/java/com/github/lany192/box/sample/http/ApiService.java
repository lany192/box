package com.github.lany192.box.sample.http;

import com.github.lany192.box.sample.bean.Area;
import com.github.lany192.box.sample.bean.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("json/city.json")
    Call<Result<List<Area>>> getCityInfo();
}
