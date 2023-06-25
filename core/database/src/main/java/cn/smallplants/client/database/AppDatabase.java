package cn.smallplants.client.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import cn.smallplants.client.database.entity.SearchHistory;
import cn.smallplants.client.database.mapper.SearchHistoryMapper;


@Database(entities = {SearchHistory.class}, exportSchema = false, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    /**
     * 搜索记录
     */
    public abstract SearchHistoryMapper searchHistoryMapper();
}
