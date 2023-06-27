package com.lany192.box.network.di

import com.lany192.box.network.data.api.ApiService
import com.lany192.box.network.repository.BoxRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideBoxRepository(apiService: ApiService): BoxRepository {
        return BoxRepository(apiService)
    }
}