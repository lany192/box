package cn.smallplants.client.database.di

import android.app.Application
import androidx.room.Room
import cn.smallplants.client.database.AppDatabase
import cn.smallplants.client.database.mapper.SearchHistoryMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "xzw.db")
            .allowMainThreadQueries().build()
    }

    @Provides
    fun provideSearchHistoryMapper(database: AppDatabase): SearchHistoryMapper {
        return database.searchHistoryMapper()
    }
}