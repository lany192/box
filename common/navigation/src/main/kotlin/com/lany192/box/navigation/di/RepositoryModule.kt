package com.lany192.box.navigation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

//    @Provides
//    fun provideDatabaseRepository(database: AppDatabase): DatabaseRepository {
//        return DatabaseRepository(database.searchHistoryDao(), database.browseHistoryDao())
//    }
}