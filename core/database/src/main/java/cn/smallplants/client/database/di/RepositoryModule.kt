package cn.smallplants.client.database.di

import cn.smallplants.client.database.AppDatabase
import cn.smallplants.client.database.repository.DatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideDatabaseRepository(database: AppDatabase): DatabaseRepository {
        return DatabaseRepository(database)
    }
}