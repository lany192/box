package cn.smallplants.client.network.di

import cn.smallplants.client.App
import cn.smallplants.client.network.api.ApiService
import cn.smallplants.client.network.interceptor.HeaderInterceptor
import com.github.lany192.arch.network.HttpLogInterceptor
import com.github.lany192.arch.utils.ListUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.Proxy
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HttpModule {

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (!ListUtils.isEmpty(App.getConfig().interceptors)) {
            for (interceptor in App.getConfig().interceptors) {
                builder.addInterceptor(interceptor)
            }
        }
        builder.addInterceptor(HeaderInterceptor())
        builder.addInterceptor(HttpLogInterceptor(App.getConfig().isDev))
        builder.retryOnConnectionFailure(true)
        builder.connectTimeout(App.getConfig().connectTimeout, TimeUnit.SECONDS)
        builder.readTimeout(App.getConfig().readTimeout, TimeUnit.SECONDS)
        builder.writeTimeout(App.getConfig().writeTimeout, TimeUnit.SECONDS)
        if (!App.getConfig().isDev) {
            builder.proxy(Proxy.NO_PROXY)
        }
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(App.getConfig().baseUrl)
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