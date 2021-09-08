package com.github.lany192.box.sample.di.module;

import com.github.lany192.box.sample.http.ApiService;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class HttpModule {

    @Singleton
    @Provides
    public Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl("https://lany192.github.io/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd HH:mm:ss")
                        .serializeNulls()
                        .create()))
                .build();
    }

    @Singleton
    @Provides
    public OkHttpClient provideClient() {
        return new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    @Singleton
    @Provides
    public ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
}