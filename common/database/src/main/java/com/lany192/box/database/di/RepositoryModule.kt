package com.lany192.box.database.di

import com.lany192.box.database.AppDatabase
import com.lany192.box.database.repository.DatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideDatabaseRepository(database: AppDatabase): DatabaseRepository {
        return DatabaseRepository(database.searchHistoryDao(), database.browseHistoryDao())
    }
}