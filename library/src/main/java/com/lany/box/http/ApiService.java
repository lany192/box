package com.lany.box.http;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface ApiService {

    @POST
    Observable<String> post(@Url String url, @Body MultipartBody body);

    @GET
    Observable<String> get(@Url String url, @QueryMap Map<String, String> map);
}