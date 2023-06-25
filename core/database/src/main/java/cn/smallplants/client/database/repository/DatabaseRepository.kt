package cn.smallplants.client.database.repository

import cn.smallplants.client.database.AppDatabase
import cn.smallplants.client.database.entity.SearchHistory
import com.github.lany192.arch.repository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * 数据库操作
 */
class DatabaseRepository(private val database: AppDatabase) : BaseRepository() {

    /**
     * 搜索历史记录
     */
    suspend fun getSearchHistories(sharedFlow: MutableSharedFlow<List<SearchHistory>>) {
        flow {
            val result = database.searchHistoryMapper().selectList(20)
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
    suspend fun getSearchHistories(record: SearchHistory) {
        flow {
            val result = database.searchHistoryMapper().insertRecord(record)
            emit(result)
        }.flowOn(Dispatchers.IO).catch { exception ->
            log.e(exception.message)
            exception.printStackTrace()
        }.collect {
            log.i("搜索历史记录插入成功：$it")
        }
    }
}