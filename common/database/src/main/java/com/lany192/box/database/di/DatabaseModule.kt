package com.lany192.box.database.di

import android.app.Application
import androidx.room.Room
import com.lany192.box.database.AppDatabase
import com.lany192.box.database.mapper.SearchHistoryMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "box.db")
            .allowMainThreadQueries().build()
    }

    @Provides
    fun provideSearchHistoryMapper(database: AppDatabase): SearchHistoryMapper {
        return database.searchHistoryMapper()
    }
}