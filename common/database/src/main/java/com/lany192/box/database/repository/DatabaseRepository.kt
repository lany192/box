package com.lany192.box.database.repository

import com.github.lany192.arch.repository.BaseRepository
import com.lany192.box.database.dao.BrowseHistoryDao
import com.lany192.box.database.dao.SearchHistoryDao
import com.lany192.box.database.entity.BrowseHistory
import com.lany192.box.database.entity.SearchHistory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * 数据库操作
 */
class DatabaseRepository(
    private val searchHistoryDao: SearchHistoryDao,
    private val browseHistoryDao: BrowseHistoryDao
) : BaseRepository() {

    /**
     * 搜索历史记录
     */
    suspend fun getSearchHistories(): Flow<List<SearchHistory>> {
        return flow {
            val result = searchHistoryDao.selectList(20)
            emit(result)
        }.flowOn(Dispatchers.IO).catch { e ->
            log.e(e.message)
            e.printStackTrace()
        }
    }

    /**
     * 保存搜索记录
     */
    suspend fun saveSearchHistory(record: SearchHistory): Flow<List<Long>> {
        return flow {
            val result = searchHistoryDao.insertRecord(record)
            emit(result)
        }.flowOn(Dispatchers.IO).catch { e ->
            log.e(e.message)
            e.printStackTrace()
        }
    }

    /**
     * 保存搜索记录
     */
    suspend fun saveBrowseHistory(record: BrowseHistory): Flow<List<Long>> {
        return flow {
            val result = browseHistoryDao.insertRecord(record)
            emit(result)
        }.flowOn(Dispatchers.IO).catch { e ->
            log.e(e.message)
            e.printStackTrace()
        }
    }
}