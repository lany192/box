package com.lany192.box.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.lany192.box.database.entity.BrowseHistory;
import com.lany192.box.database.entity.SearchHistory;
import com.lany192.box.database.dao.BrowseHistoryDao;
import com.lany192.box.database.dao.SearchHistoryDao;


@Database(entities = {SearchHistory.class, BrowseHistory.class}, exportSchema = false, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    /**
     * 搜索记录
     */
    public abstract SearchHistoryDao searchHistoryDao();

    /**
     * 浏览记录
     */
    public abstract BrowseHistoryDao browseHistoryDao();
}
