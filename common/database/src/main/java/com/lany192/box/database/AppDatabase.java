package com.lany192.box.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.lany192.box.database.entity.SearchHistory;
import com.lany192.box.database.mapper.BrowseHistoryMapper;
import com.lany192.box.database.mapper.SearchHistoryMapper;


@Database(entities = {SearchHistory.class}, exportSchema = false, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    /**
     * 搜索记录
     */
    public abstract SearchHistoryMapper searchHistoryMapper();

    /**
     * 浏览记录
     */
    public abstract BrowseHistoryMapper browseHistoryMapper();
}
