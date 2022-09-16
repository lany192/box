package com.lany192.box.sample.di

import com.github.lany192.arch.network.HttpLogInterceptor
import com.github.lany192.arch.network.ParamsInterceptor
import com.github.lany192.arch.utils.UserInfo
import com.lany192.box.sample.BuildConfig
import com.lany192.box.sample.data.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HttpModule {

    @Singleton
    @Provides
    fun provideClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .addInterceptor(ParamsInterceptor(object : UserInfo {
                override fun getUserId(): Int {
                    return 0
                }

                override fun getToken(): String {
                    return ""
                }

            }))
            .addInterceptor(HttpLogInterceptor(BuildConfig.DEBUG))
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.wanandroid.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}