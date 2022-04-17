package com.lany192.box.sample.di

import com.lany192.box.sample.data.api.ApiService
import com.lany192.box.sample.repository.BoxRepository
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