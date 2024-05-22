package com.lany192.box.database.mapper

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lany192.box.database.entity.SearchHistory

/**
 * 搜索记录表操作
 */
@Dao
interface SearchHistoryMapper {
    /**
     * 插入数据
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(vararg records: SearchHistory): List<Long>

    /**
     * 删除记录
     */
    @Query("DELETE FROM game_search_history WHERE keyword == :keyword")
    suspend fun deleteByKeyword(keyword: String)

    /**
     * 删除全部记录
     */
    @Query("DELETE FROM game_search_history")
    suspend fun deleteAll()

    /**
     * 查询数据
     */
    @Query("SELECT * FROM game_search_history ORDER by _id DESC LIMIT :limit ")
    suspend fun selectList(limit: Int): List<SearchHistory>
}