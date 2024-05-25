package com.lany192.box.database.repository

import com.github.lany192.arch.repository.BaseRepository
import com.lany192.box.database.AppDatabase
import com.lany192.box.database.dao.BrowseHistoryDao
import com.lany192.box.database.dao.SearchHistoryDao
import com.lany192.box.database.entity.BrowseHistory
import com.lany192.box.database.entity.SearchHistory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * 数据库操作
 */
class DatabaseRepository() : BaseRepository() {
    @Inject
    lateinit var searchHistoryDao: SearchHistoryDao
    @Inject
    lateinit var browseHistoryDao: BrowseHistoryDao

    /**
     * 搜索历史记录
     */
    suspend fun getSearchHistories(sharedFlow: MutableSharedFlow<List<SearchHistory>>) {
        flow {
            val result = searchHistoryDao.selectList(20)
            emit(result)
        }.flowOn(Dispatchers.IO).catch { exception ->
            log.e(exception.message)
            exception.printStackTrace()
        }.collect {
            //liveData.postValue(it)
        }
    }

    /**
     * 保存搜索记录
     */
    suspend fun saveSearchHistory(record: SearchHistory) {
        flow {
            val result = searchHistoryDao.insertRecord(record)
            emit(result)
        }.flowOn(Dispatchers.IO).catch { exception ->
            log.e(exception.message)
            exception.printStackTrace()
        }.collect {
            log.i("搜索历史记录插入成功：$it")
        }
    }

    /**
     * 保存搜索记录
     */
    suspend fun saveBrowseHistory(record: BrowseHistory) {
        flow {
            val result = browseHistoryDao.insertRecord(record)
            emit(result)
        }.flowOn(Dispatchers.IO).catch { exception ->
            log.e(exception.message)
            exception.printStackTrace()
        }.collect {
            log.i("搜索记录插入成功：$it")
        }
    }
}