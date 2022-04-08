package com.lany192.box.sample.di

import com.elvishew.xlog.XLog
import com.google.gson.GsonBuilder
import com.lany192.box.sample.BuildConfig
import com.lany192.box.sample.data.api.ApiService
import com.lany192.box.sample.repository.BoxRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class HttpModule {

    @Singleton
    @Provides
    fun provideClient(): OkHttpClient {
        val logInterceptor = HttpLoggingInterceptor(XLog::i)
        if (BuildConfig.DEBUG) {
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .addNetworkInterceptor(logInterceptor) //设置打印拦截日志
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
            .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setDateFormat("yyyy-MM-dd HH:mm:ss")
                        .serializeNulls()
                        .create()
                )
            )
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideBoxRepository(apiService: ApiService): BoxRepository {
        return BoxRepository(apiService)
    }
}