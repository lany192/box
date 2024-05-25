package com.lany192.box.database.di

import android.app.Application
import androidx.room.Room
import com.lany192.box.database.AppDatabase
import com.lany192.box.database.dao.BrowseHistoryDao
import com.lany192.box.database.dao.SearchHistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room
            .databaseBuilder(application, AppDatabase::class.java, "box.db")
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    fun provideSearchHistoryDao(database: AppDatabase): SearchHistoryDao {
        return database.searchHistoryDao()
    }

    @Provides
    fun provideBrowseHistoryDao(database: AppDatabase): BrowseHistoryDao {
        return database.browseHistoryDao()
    }
}