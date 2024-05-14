package com.lany192.box.network.di

import com.github.lany192.arch.network.HttpLogInterceptor
import com.github.lany192.arch.network.ParamsInterceptor
import com.lany192.box.network.BuildConfig
import com.lany192.box.network.interceptor.DomainInterceptor
import com.lany192.box.network.interceptor.TimeIntervalInterceptor
import com.lany192.box.network.data.api.ApiService
import com.lany192.box.network.interceptor.HeaderInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HttpModule {

    @Singleton
    @Provides
    fun provideClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.retryOnConnectionFailure(true)
        builder.addInterceptor(DomainInterceptor())
        builder.addInterceptor(HeaderInterceptor())
        builder.addInterceptor(ParamsInterceptor(HashMap<String, String>()))
        builder.addInterceptor(HttpLogInterceptor(BuildConfig.DEBUG))
        builder.addInterceptor(TimeIntervalInterceptor())
        builder.connectTimeout(7, TimeUnit.SECONDS)
        builder.readTimeout(7, TimeUnit.SECONDS)
        builder.writeTimeout(7, TimeUnit.SECONDS)

        //反代理抓包，可防止charles 和 fiddler抓包 https://www.jianshu.com/p/9921db646c59
//            builder.proxy(Proxy.NO_PROXY)
        //忽略证书校验
//        builder.sslSocketFactory(getSSLSocketFactory())
//        //忽略域名校验
//        builder.hostnameVerifier { hostname: String, session: SSLSession? ->
//            XLog.i("hostname：$hostname")
//            true
//        }
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        return Retrofit.Builder()
            .baseUrl("https://www.wanandroid.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}