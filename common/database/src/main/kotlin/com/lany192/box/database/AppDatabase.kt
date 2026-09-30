package com.lany192.box.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lany192.box.database.dao.BrowseHistoryDao
import com.lany192.box.database.dao.SearchHistoryDao
import com.lany192.box.database.entity.BrowseHistory
import com.lany192.box.database.entity.SearchHistory

@Database(
    entities = [SearchHistory::class, BrowseHistory::class],
    exportSchema = false,
    version = 1
)
@TypeConverters(
    Converters::class
)
abstract class AppDatabase : RoomDatabase() {
    /**
     * 搜索记录
     */
    abstract fun searchHistoryDao(): SearchHistoryDao

    /**
     * 浏览记录
     */
    abstract fun browseHistoryDao(): BrowseHistoryDao
}
